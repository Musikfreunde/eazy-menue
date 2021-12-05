<template lang="html">

  <section class="verlauf">

    <div>
      <history-table/>
    </div>

  </section>

</template>

<script lang="js">

import HistoryTable from '../components/HistoryTable'
import { api } from '../api'
export default {
  name: 'verlauf',
  components: { HistoryTable },
  props: [],
  data () {
    return {
      text: ''
    }
  },
  async beforeRouteEnter (to, from, next) {
    next(async vm => {
      const response = await api.getUserOrders(vm.$keycloak.idTokenParsed.preferred_username)
      await vm.$store.commit('setUserOrders', response.data)
    })
  },
  async beforeRouteUpdate (to, from, next) {
    next()
  },
  computed: {

  }
}

</script>

<style scoped lang="scss">
  #searchInput{
    width: 15%;
  }
  .searchContainer{
    margin: 2%;
  }
</style>
