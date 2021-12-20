<template>
  <GChart
      type="PieChart"
      :data="chartData"
      :options="chartOptions"
  />
</template>

<script lang="js">
import { GChart } from 'vue-google-charts'
import { api } from '@/api'

export default {
  data () {
    return {
      chartData: [],
      chartOptions: {
        chart: {
          title: 'Company Performance',
          subtitle: 'Sales, Expenses, and Profit: 2014-2017'
        }
      },
      name: 'Statistiken',
      components: {
        GChart
      }
    }
  },
  async beforeRouteEnter (to, from, next) {
    next(async vm => {
      const allCatsResp = await api.getAllCategories()
      const catsUserResp = await api.getCategoriesForUserName(vm.$keycloak.idTokenParsed.preferred_username)
      vm.chartData.push(['Category', 'Amount'])
      for (let i = 0; i < allCatsResp.data.length; i++) {
        for (let j = 0; j < catsUserResp.data.length; j++) {
          if (catsUserResp.data[j] === allCatsResp.data[i]) {
            vm.chartData.push([allCatsResp.data[i], 1])
          } else {
            vm.chartData.push([allCatsResp.data[i], 0])
          }
        }
      }
    })
  }
}
</script>

<style scoped lang="scss">

</style>
