<!-- This Vue component will act as the statistics page to show visual information to the user. -->
<template lang="html">
  <v-container class="py-12 height_class">
    <v-col align="center">
      <!-- We have a basic title here to show before the visualization. -->
        <b><h3 class="display-1 py-6" style="color:black;">Let's visualize this user's information.</h3></b>
        <br />
         <zingchart :data="pieConfig" :series="this.mydata3"></zingchart>
         <zingchart :data="followerConfig" :series="[{ values: this.mydata4}]"></zingchart>   





        <!-- This multiselect allows the user to pick a country to visualize. -->
        <div style="width:50%; align:center;">
            <multiselect
            :options="options"
             v-model="selected">
            </multiselect>
       </div>
       <br />
       <!-- The button below allows the user to render the graph and see the latest information. -->
       <v-btn @click="getData();">Visualize Query</v-btn>
    </v-col>

    <!-- Now we can make the bar chart with the help of the information from the API calls. -->
    
    <!-- Now we can have one line to further describe the information we have at our disposal. -->
    <v-col align="center">
      <br>
        <p style="align: center;">We are now analyzing <u>{{this.selected}}</u> of the user sorted by name to gather statistics and information about various data.</p>
    </v-col>
    <!-- Now we can make the pie chart with the help of the information from the API calls. -->
   


    <!-- Now we can make the pie chart with the help of the information from the API calls. -->
    <zingchart :data="chartConfig" :series="[{ values: this.mydata}]"></zingchart>
        <v-col align="center">
        <p style="align: center;">We can see above that there is a general trend of a spike in forks as the commits increases initially, but then it tends to taper off as the commits increase. This is seen clearly if we increase the repositories shown to 100%.</p>
    </v-col>
    <br>
    <zingchart :data="chart2Config" :series="[{ values: this.mydata2}]"></zingchart>

    
     <v-col align="center">
    <p style="align: center;">We see a similar pattern above where there is a spike in the size of a repository as the numbers of forks increases initially, but it also tends to taper off as the number of forks increase. This is seen clearly if we increase the repositories shown to 100%.</p>
    </v-col>
    <br>


  </v-container>
</template>

<script>
import "zingchart";
import zingchartVue from "zingchart-vue";
import axios from "axios";
import Multiselect from "vue-multiselect";
//[[0,1], [1,2], [2,3], [4,5], [5,6]]


export default {
  components: {
    zingchart: zingchartVue,
     Multiselect
  },
  data() {
    return {
      mydata : [],
      mydata2 : [],
      mydata3 : [],
      mydata4 : [
        {
          text: 'Christopher',
          value: '675'
        },
        {
          text: 'Aaron',
          value: '50'
        },
        {
          text: 'Nick',
          value: '275'
        }
      ],
      chartConfig: {
        type: "bar3d",
        title: {
          text: "Increase in Number of Forks with Number of Commits" + "\n" + "\n",
          marginTop:"dynamic",
        },
        plotarea: {
    margin:'dynamic',
    marginTop:"-10%",
  },
        plot : {
        animation: {
              "effect": "ANIMATION_FADE_IN",
              "speed": "15000",
              "method": "5",
              "sequence": "1"
        },
        "tooltip": {
              "text": "This repository has %kt commits (X) and %vt forks (Y)."
        },
        
        },
        // series: [
        //   {
        //     values: mydata
        //   },
        // ],
        scaleX: {
          label: { /*Add a scale title with a label object*/
            text: "Commits",
            'font-size': "20",
        },

        },
        scaleY: {
          label: { /*Add a scale title with a label object*/
            text: "Forks",
            'font-size': "20",
        },


        },
      },
            chart2Config: {
        type: "bar3d",
        title: {
          text: "Increase in Size of a repository (KB) with Number of Forks" + "\n" + "\n",
          
        },
        plotarea: {
    margin:'dynamic',
    marginTop:"-10%",

  },
        plot : {
        animation: {
              "effect": "ANIMATION_FADE_IN",
              "speed": "15000",
              "method": "5",
              "sequence": "1"
        },
        "tooltip": {
              "text": "This repository has %kt Forks (X) and a Size of %vt KB (Y)."
        },
        
        },
        // series: [
        //   {
        //     values: mydata
        //   },
        // ],
        scaleX: {
          label: { /*Add a scale title with a label object*/
            text: "Forks",
            'font-size': "20",
        },

        },
        scaleY: {
          label: { /*Add a scale title with a label object*/
            text: "Size (KB)",
            'font-size': "20",
        },


        },
      },
pieConfig: {
                type: "pie3d",
                legend: {
                   marginTop:"-15%",
                },
                title: {
                  text: "All languages used in repositories with their associated total commits",
                  align: "center",
                },
                plotarea: {
                  marginTop:"dynamic",
                  marginLeft:"dynamic",
                  marginRight:"dynamic"
                },
            },

            followerConfig: {
                type: "gauge",
                  globals: {
    fontSize: 25
  },
  plotarea: {
    marginTop: 80
  },
  plot: {
    size: '100%',
    valueBox: {
      placement: 'center',
      text: '%v', //default
      fontSize: 35,
      rules: [{
          rule: '%v >= 700',
          text: '%v<br>EXCELLENT'
        },
        {
          rule: '%v < 700 && %v > 640',
          text: '%v<br>Good'
        },
        {
          rule: '%v < 640 && %v > 580',
          text: '%v<br>Fair'
        },
        {
          rule: '%v <  580',
          text: '%v<br>Bad'
        }
      ]
    }}
            },
      //Make the default country Brazil, but the user can change this.
            selected: "10% of repositories",
            //We have options for the drop down menu.
            options: ["10% of repositories", "20% of repositories", "30% of repositories", "40% of repositories", "50% of repositories", "60% of repositories", "70% of repositories", "80% of repositories", "90% of repositories", "100% of repositories"],
            
    };
  },
    methods: {
        getData(){
      axios
        .get(
          "http://localhost:8080/repo",
          {
            headers: {

            },
          }
        )
        .then((response) => {
          this.mydata = [];
          const numberOfRepos = Object.keys(response.data).length;
          const percent =  parseInt(this.selected.substring(0, 4).match(/\d|\.|-/g).join('')) / 100;
          console.warn(percent);
          for(var i = 0; i < numberOfRepos*percent; i++){
            //if(response.data[i].NumberOfCommits < numberOfRepos / 10 ){
this.mydata.push([response.data[i].NumberOfCommits,response.data[i].NumberOfForks]);
  // }
            
            //this.items[i].name = response.data[i].RepositoryName;
          }
         
          console.warn("hi");
          console.warn(this.mydata)
          //this.mydata.push([6,7]);
          // console.warn(add)
           console.warn(response);

          this.mydata2 = [];
          for(var j = 0; j < numberOfRepos*percent; j++){
            //if(response.data[i].NumberOfCommits < numberOfRepos / 10 ){
            this.mydata2.push([response.data[j].NumberOfForks,response.data[j].Size]);
          }

          const map = new Map();
          for(var z = 0; z < numberOfRepos; z++){
            //if(response.data[i].NumberOfCommits < numberOfRepos / 10 ){
            //this.mydata2.push([response.data[j].NumberOfForks,response.data[j].Size]);
            if(map.has(response.data[z].Language)){
              map.set(response.data[z].Language, response.data[z].NumberOfCommits + map.get(response.data[z].Language));
            }
            else {
              map.set(response.data[z].Language, response.data[z].NumberOfCommits);
            }
          }

          //Now map1 has all the languages as keys, and the number of commits as values.
          map.delete(null);
          console.warn(map);
          for (const [key, value] of map) {
            this.mydata3.push({
             
              text: key.toString() + " (" + value + " commits)",
               values: [value],
              //detached: true
            });

          }

          console.warn(this.mydata3);


        })
        .catch((e) => {
          /* eslint-disable no-console */
          console.log(e);
          /* eslint-enable no-console */
        });
        }
        },
        


        created() {
    this.getData();
  },
};

// mydata.push(add);
</script>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>
<style style="css" scoped>
.height_class {
  min-height: 100vh;
}
</style>
