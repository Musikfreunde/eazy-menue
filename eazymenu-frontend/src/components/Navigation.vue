<template lang="html">

  <section class="navbar">
    <b-navbar id="navbar" fixed="top" variant="dark" >
        <b-button class="iconButton"  v-b-toggle.sidebar_left><b-icon icon="list" style="color: white" ></b-icon></b-button>
        <b-button id="backButton" class="iconButton" variant="dark" v-if="this.$route.name !== 'Uebersicht'" @click="$router.push('/')"><b-icon icon="arrow-left"></b-icon> </b-button>
        <h1><strong> Menübestellung</strong> </h1>
    </b-navbar>
    <b-sidebar backdrop-variant="dark" backdrop id="sidebar_left" title="Navigation" shadow="lg">
      <template #footer="{ hide }">
        <div class="d-flex bg-dark text-light align-items-center px-3 py-2">
          <strong class="mr-auto">{{$keycloak.idTokenParsed.preferred_username}}</strong>
          <b-button class="logout"   size="sm" @click="hide,$keycloak.logout()">Ausloggen</b-button>
        </div>
      </template>
      <nav class="mb-3">
        <b-list-group>
          <b-list-group-item @click="$router.push('/')" button v-if="$route.name !== 'Uebersicht'">Übersicht</b-list-group-item>
          <b-list-group-item @click="$router.push('/verlauf')" button v-if="$route.name !== 'Verlauf' && this.$keycloak.hasRealmRole('mitarbeiter')">Bestellverlauf</b-list-group-item>
          <b-list-group-item @click="$router.push('/statistiken')" button v-if="$route.name !== 'Statistiken' && this.$keycloak.hasRealmRole('mitarbeiter')">Statistiken</b-list-group-item>
          <b-list-group-item @click="$router.push('/drucken')" button v-if="this.$keycloak.hasRealmRole('kantine') && $route.name !== 'Drucken'">Drucken</b-list-group-item>
        </b-list-group>
      </nav>
    </b-sidebar>
  </section>

</template>

<script lang="js">

export default {
  name: 'navbar',
  props: [],
  data () {
    return {

    }
  },
  methods: {

  },
  computed: {

  }
}

</script>

<style scoped lang="scss">
  .navbar {
    width: 100%;
    margin-bottom: 50px;
  }
  h1{
    color: white;
    margin-left: 1%;
    font-size: large;
    vertical-align: middle;
  }
  .logout{
    margin-left: auto;
  }
  .iconButton{
    margin-left: 1%;
  }
  #navbar{
    position: fixed;
  }
  #backButton:hover{
    background-color: #191919;
  }
</style>
