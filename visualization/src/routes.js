import HomePage from "./pages/homePage.vue";
import VisualizationPage from "./pages/visualizationPage.vue";

//Set the routes for the pages in the application.
export const routes = [
  {
    path: "/",
    component: HomePage,
    meta: {
      metaTags: [
        {
          name: "Home Page",
          content: "This is the first page",
        },
      ],
    },
  },
  {
    path: "/visualization",
    component: VisualizationPage,
    meta: {
      metaTags: [
        {
          name: "Visualization Page",
          content: "This is the second page",
        },
      ],
    },
  },
];
