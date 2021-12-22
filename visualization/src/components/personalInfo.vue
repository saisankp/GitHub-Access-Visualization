<!-- This Vue component will act as a overview box which shows information about the closed cases in Ireland. -->
<template lang="html">
  <v-card style="background: #4da6ff;">
    <v-row>
      <!-- We can now simply show information from the COVID-API after the API call. -->
      <v-col align="center">
        <h3 style="color:white;" class="display-1 py-6"><b>Personal Information</b></h3>
        <p style="color:white;" class="headline">Name : {{ info.name }}</p>
        <p style="color:white;" class="headline">Location : {{ info.location }}</p>
        <p style="color:white;" class="headline">Hirable : {{ info.hirable }}</p>
        <!-- After pressing the "Get Data" button, we want to update the data shown. -->
        <v-btn @click="goToHome">Linked Website</v-btn>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import axios from "axios";
export default {
  //We have some default values for some information before the API call is made.
  //If this shows for a long time, then there is a high chance that the API key is wrong.
  data: () => ({
    info: {
      location: "...",
      hirable: "...",
      name: "...",
      creation: "...",
      site: "..."
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
          this.info.location = response.data[0].Location;
          this.info.hirable = response.data[0].Hirable;
          if(this.info.hirable == false){
            this.info.hirable = "No";
          } else{
            this.info.hirable = "Yes"
          }
          this.info.name = response.data[0].Name;
          this.info.site = response.data[0].LinkedBlogURL;
          //console.warn(response);
        })
        .catch((e) => {
          /* eslint-disable no-console */
          console.log(e);
          /* eslint-enable no-console */
        });
    },
    goToHome(){
    window.location.href = this.info.site;
      }
  },
  created() {
    this.getData();
  },
};
</script>

<style lang="css" scoped>

</style>
