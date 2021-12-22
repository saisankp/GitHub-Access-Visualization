<!-- This Vue component will act as a simple footer shown on the bottom of every page in the application. -->
<!-- This footer was developed with the help of Vuetify and is simple but effective at showing the user some basic information at the end of a page -->
<template lang="html">
  <v-footer dark padless>
    <!-- We want a blue footer -->
    <v-card flat tile class="blue lighten-1 white--text text-center">
      <v-card-text>
        <!-- We want a GitHub Icon to let the user see our GitHub repository to view these source files! -->
       <v-btn v-for="link in links" :key="link.id" class="mx-4" icon :href="link.url">
            <v-icon size="24px" color="white">{{ link.icon }}</v-icon>
          </v-btn>
      </v-card-text>
      <!-- We can now show some basic information about the application -->
      <v-card-text class="white--text">
        This application allows you to visualize information from a GitHub User using the official <a style="color:white;" href="https://docs.github.com/en/rest">GitHub API</a>. This project was completed for the module <a style="color:white;" href="https://teaching.scss.tcd.ie/module/csu33012-software-engineering/">Software Engineering Project</a> taken during the 3rd year of Computer Science at Trinity College Dublin. You can find data on the <a style="color:white;" href="\">Home Page</a> and visualizations on the <a style="color:white;" href="\visualization">Visualization Page</a>.
      </v-card-text>
      <v-divider></v-divider>
      <!-- Now we can finish the footer with some final text -->
      <v-card-text class="white--text">
        &copy;{{ new Date().getFullYear() }} <strong>Copyright <a style="color:white;" href="https://github.com/saisankp"> Prathamesh Sai Sankar</a> </strong>
      </v-card-text>
    </v-card>
  </v-footer>
</template>

<script>
 export default {
  data: () => ({
    //We want to store information about our GitHub here.
    links: [
      {
        id: 1,
        icon: "mdi-github",
        url: "https://github.com/saisankp/GitHub-Access",
      },
    ],
  }),
};
</script>