<!-- This Vue component will act as the statistics page to show visual information to the user. -->
<template lang="html">
  <v-container class="py-12 height_class">

    <!-- Now we can make the pie chart with the help of the information from the API calls. -->
    <zingchart :data="chartConfig" :series="[{ values: this.mydata}]"></zingchart>
  </v-container>
</template>

<script>
import "zingchart";
import zingchartVue from "zingchart-vue";
import axios from "axios";
//[[0,1], [1,2], [2,3], [4,5], [5,6]]


export default {
  components: {
    zingchart: zingchartVue,
  },
  data() {
    return {
      mydata : [],
      chartConfig: {
        type: "line",
        title: {
          text: "Increase in Number of Forks with Number of Commits",
        },
        plot : {
        animation: {
              "effect": "ANIMATION_FADE_IN",
              "speed": "15000",
              "method": "5",
              "sequence": "1"
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
        },
        },
        scaleY: {
          label: { /*Add a scale title with a label object*/
            text: "Forks",
        },

        },
      },
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
          const numberOfRepos = Object.keys(response.data).length;
          for(var i = 0; i < numberOfRepos; i++){
            if(response.data[i].NumberOfCommits < 2000){
this.mydata.push([response.data[i].NumberOfCommits,response.data[i].NumberOfWatchers]);
            }
            
            //this.items[i].name = response.data[i].RepositoryName;
          }
          console.warn("hi");
          console.warn(this.mydata)
          //this.mydata.push([6,7]);
          // console.warn(add)
           console.warn(response);
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
