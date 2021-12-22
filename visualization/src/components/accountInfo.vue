<!-- This Vue component will act as a overview box which shows personal information about the chosen user. -->
<template lang="html">
  <v-card style="background: #4da6ff;">
    <v-row>
      <!-- We can now simply show the account information after the API call to our server at port 8081. -->
      <v-col align="center">
        <h3 style="color:white;" class="display-1 py-6"><b>Account Information</b></h3>
        <p style="color:white;" class="headline">Followers : {{ info.followers }}</p>
        <p style="color:white;" class="headline">Following : {{ info.following }}</p>
        <p style="color:white;" class="headline">Account created : {{ info.creation }}</p>
        <!-- After pressing the "GitHub Account" button, we want to go to the user's GitHub Account. -->
        <v-btn @click="goToGitHubAccount">GitHub Account</v-btn>
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
      name: "...",
      followers: "...",
      following: "...",
      creation: "...",
      github: "..."
    },
    object: null,
  }),
  methods: {
    //Now we can make the API call.
    getData() {
      axios
        .get("http://localhost:8081/userdata")
        .then((response) => {
          //Set all account information from the API call.
          this.info.name = response.data[0].UserName;
          this.info.followers = response.data[0].Followers;
          this.info.following = response.data[0].Following;
          this.info.creation = (response.data[0].AccountCreation).substring(8,10) + "/" + (response.data[0].AccountCreation).substring(5,7) + "/" +  (response.data[0].AccountCreation).substring(0,4);
          this.info.github = response.data[0].GitHubAccountURL;
        })
        .catch((e) => {
          /* eslint-disable no-console */
          console.log(e);
          /* eslint-enable no-console */
        });
    },
    goToGitHubAccount(){
    window.location.href = this.info.github;
      }
  },
  created() {
    this.getData();
  },
};
</script>
<style lang="css" scoped>
</style>