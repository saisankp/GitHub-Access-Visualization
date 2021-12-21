<!-- This Vue component show basic textual information about global COVID-19 statistics to the user -->
<template lang="html">
  <v-container>
    <v-row>
      <v-col align="center">
        <!--We can call the responses from the API call and print them out with some other text. -->
        <h3 id="theTitle" class="display-1 py-6">
           Welcome to my Software Engineering Metric Visualization. <br> The user that you have chosen for this visualization is <span class="blue--text">{{ info.name }}</span>.
        </h3>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import axios from "axios";
export default {
  //We have some default values for some information before the API call is made.
  //If this shows for a long time, then there is a high chance that the API key is wrong.
  data: () => ({
    info: {
      name: "...",
      followers: "...",
      following: "...",
    },
    object: null,
  }),
  methods: {
    //Now we can make the API call.
    getData() {
      axios
        .get(
          "http://localhost:8080/userdata",
          {
            headers: {

            },
          }
        )
        .then((response) => {
          this.info.name = response.data[0].UserName;
          this.info.followers = response.data[0].Followers;
          this.info.following = response.data[0].Following;
          //console.warn(response);
        })
        .catch((e) => {
          /* eslint-disable no-console */
          console.log(e);
          /* eslint-enable no-console */
        });
    },
  },
  created() {
    this.getData();
  },
};
</script>


<style lang="css" scoped>
</style>
