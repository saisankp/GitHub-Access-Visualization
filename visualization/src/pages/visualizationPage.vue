<!-- This Vue component will act as the visualization page to show visual information from the GitHub API's data to the user. -->
<template lang="html">
  <v-container class="py-12 height_class">
    <v-col align="center">
        <!-- We have a basic title here to show before the user specific visualization. -->
        <b><h3 class="display-1 py-6" style="color:black;">Visualize user specific information.</h3></b>
        <br/>
        <!--This visualization with show a pie chart of all the languages used by the chosen user along with their associated total commits. -->
        <zingchart :data="pieConfig" :series="this.languagesAndCommits"></zingchart>
        <!--This visualization with show a bar chart containing the number of followers the chosen user follows, and the number of followers the chosen user has. -->
        <zingchart :data="followerConfig" :series="[{ values: this.followersAndFollowing}]"></zingchart>
        <!-- We have a basic title here to show before the repository specific visualization. -->   
        <b><h3 class="display-1 py-6" style="color:black;">Visualize repository specific information.</h3></b>
        <br/>
        <!-- This multiselect allows the user to pick the amount of repositories to visualize. -->
        <div style="width:50%; align:center;">
            <multiselect
            :options="options"
             v-model="selected">
            </multiselect>
       </div>
       <br/>
       <!-- The button below allows the user to render the visualizations after their query. -->
       <v-btn @click="setRepoData(); setUserData()">Visualize Query</v-btn>
    </v-col>

    <!-- Now we can have one line to describe the query from the user before visualizing the data. -->
    <v-col align="center">
      <br>
        <p style="align: center;">We are now analyzing <u>{{this.selected}}</u> of the user sorted by name to gather statistics and information about various data.</p>
    </v-col>

    <!-- Now we can make the 3d bar chart with the help of the information from the API calls. -->
    <zingchart :data="chartConfig" :series="[{ values: this.forksAndCommits}]"></zingchart>

    <!-- Now we can have one line to describe the visualization above. -->
    <v-col align="center">
        <p style="align: center;">We can see above that there is a general trend of a spike in forks as the commits increases initially, but then it tends to taper off as the commits increase. This is seen clearly if we increase the repositories shown to 100%.</p>
    </v-col>
    <br>

    <!-- Now we can make the 3d bar chart with the help of the information from the API calls. -->
    <zingchart :data="chart2Config" :series="[{ values: this.sizeAndForks}]"></zingchart>

    <!-- Now we can have one line to describe the visualization above. -->
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
export default {
  components: {
    zingchart: zingchartVue,
     Multiselect
  },
  data() {
    return {
      forksAndCommits : [],
      sizeAndForks : [],
      languagesAndCommits : [],
      followersAndFollowing : [],
      done : 0,
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
        scaleX: {
          label: {
            text: "Forks",
            'font-size': "20",
        },
        },
        scaleY: {
          label: {
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
         type: 'bar', 
         title: { 
            text: 'Followers vs Following', 
          }, 
         scaleX: { 
            labels: ['Followers', 'Following'] 
          }, 
          plotarea: { 
            marginLeft:'dynamic', 
            marginRight:'dynamic' 
          },
       },
    //Make the default amount 10% of repositories, but the user can change this.
      selected: "10% of repositories",
      //We have options for the drop down menu.
      options: ["10% of repositories", "20% of repositories", "30% of repositories", "40% of repositories", "50% of repositories", "60% of repositories", "70% of repositories", "80% of repositories", "90% of repositories", "100% of repositories"],
    };
  },
    methods: {
        setRepoData(){
      axios
        .get("http://localhost:8081/repo")
        .then((response) => {

          //Set data for forks and commits graph.
          this.forksAndCommits = [];
          const numberOfRepos = Object.keys(response.data).length;
          const percent =  parseInt(this.selected.substring(0, 4).match(/\d|\.|-/g).join('')) / 100;
          for(var i = 0; i < numberOfRepos*percent; i++){
            this.forksAndCommits.push([response.data[i].NumberOfCommits,response.data[i].NumberOfForks]);
          }

          //Set data for size and forks graph.
          this.sizeAndForks = [];
          for(var j = 0; j < numberOfRepos*percent; j++){
            this.sizeAndForks.push([response.data[j].NumberOfForks,response.data[j].Size]);
          }
          const map = new Map();
          for(var z = 0; z < numberOfRepos; z++){
            if(map.has(response.data[z].Language)){
              map.set(response.data[z].Language, response.data[z].NumberOfCommits + map.get(response.data[z].Language));
            }
            else {
              map.set(response.data[z].Language, response.data[z].NumberOfCommits);
            }
          }

          //Set data for language and commits graph.
          this.languagesAndCommits = [];
          map.delete(null);
          for (const [key, value] of map) {
            this.languagesAndCommits.push({
              text: key.toString() + " (" + value + " commits)",
               values: [value],
            });
          }
        })
        .catch((e) => {
          /* eslint-disable no-console */
          console.log(e);
          /* eslint-enable no-console */
        });
        },
    setUserData(){
      axios
        .get("http://localhost:8081/userdata")
        .then((response) => {
          //Set data for followers and following bar chart.
          this.followersAndFollowing = [];
          this.followersAndFollowing.push(response.data[0].Followers);
          this.followersAndFollowing.push(response.data[0].Following);
        })
        .catch((e) => {
          /* eslint-disable no-console */
          console.log(e);
          /* eslint-enable no-console */
        });
        }
        },
  created() {
    this.setRepoData();
    this.setUserData();
  },
};
</script>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>
<style style="css" scoped>
.height_class {
  min-height: 100vh;
}
</style>