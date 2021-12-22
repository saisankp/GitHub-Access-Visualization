<!-- This Vue component will act as the home page, and will import other components to keep this component easy to understand. -->
<template lang="html">
  <v-container class="py-12 height_class">
    <!-- We want our application overview component first. -->
    <Overview></Overview>
    <v-row>
      <!-- Now we can show the account information and personal information of the user. -->
      <v-col><Account></Account></v-col>
      <v-col><Personal></Personal></v-col>
    </v-row>
    <v-row class="pt-12">
      <!-- Finally we can show the data table containing the chosen user's repositories. -->
      <Table></Table>
    </v-row>
  </v-container>
</template>

<script>
import Overview from "../components/applicationOverview";
import Account from "../components/accountInfo";
import Personal from "../components/personalInfo";
import Table from "../components/repoTable";
export default {
  components: {
    Overview,
    Personal,
    Table,
    Account,
  }
};
</script>

<style lang="css" scoped>
.height_class {
  min-height: 100vh;
}
</style>