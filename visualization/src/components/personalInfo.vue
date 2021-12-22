<!-- This Vue component will act as a overview box which shows personal information about the chosen user. -->
<template lang="html">
  <v-card style="background: #4da6ff;">
    <v-row>
      <!-- We can now simply show the personal information after the API call to our server at port 8081. -->
      <v-col align="center">
        <h3 style="color:white;" class="display-1 py-6"><b>Personal Information</b></h3>
        <p style="color:white;" class="headline">Name : {{ info.name }}</p>
        <p style="color:white;" class="headline">Location : {{ info.location }}</p>
        <p style="color:white;" class="headline">Hirable : {{ info.hirable }}</p>
        <!-- After pressing the "Linked Website" button, we want to go to the Linked Website on the user's GitHub Account. -->
        <v-btn @click="goToPersonalSite">Linked Website</v-btn>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import axios from "axios";
export default {
  //We have some default values for some information before the API call is made.
  //If this shows for a long time, then there is a high chance that the server at port 8081 is not running.
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
        .get("http://localhost:8081/userdata")
        .then((response) => {
          //Set all personal information from the API call.
          this.info.location = response.data[0].Location;
          this.info.hirable = response.data[0].Hirable;
          if(this.info.hirable == false){
            this.info.hirable = "No";
          } else{
            this.info.hirable = "Yes"
          }
          this.info.name = response.data[0].Name;
          this.info.site = response.data[0].LinkedBlogURL;
        })
        .catch((e) => {
          /* eslint-disable no-console */
          console.log(e);
          /* eslint-enable no-console */
        });
    },
    goToPersonalSite(){
    window.location.href = "https://" + this.info.site;
      }
  },
  created() {
    this.getData();
  },
};
</script>
<style lang="css" scoped>
</style>