<template>
  <b-tabs id="stat-tabs" >
    <b-tab title="Kategorien" active><GChart
      type="PieChart"
      :data="chartData"
      :options="chartOptions"
      :resizeDebounce="10"
    /></b-tab>
    <b-tab title="Wochentage" ><GChart
      type="ColumnChart"
      :data="chartDataDays"
      :options="chartOptions"
      :resizeDebounce="10"
    /></b-tab>
  </b-tabs>

</template>

<script lang="js">
import { GChart } from 'vue-google-charts'
import { api } from '@/api'

export default {
  data () {
    return {
      chartDataDays: [],
      chartData: [],
      name: 'Statistiken',
      components: {
        GChart
      }
    }
  },
  computed: {
    chartOptions () {
      return {
        chart: {
          title: 'Company Performance',
          subtitle: 'Sales, Expenses, and Profit: 2014-2017'
        },
        width: window.innerWidth / 100 * 90,
        height: window.innerHeight / 100 * 50
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
          if (catsUserResp.data[j].category === allCatsResp.data[i]) {
            vm.chartData.push([allCatsResp.data[i], catsUserResp.data[j].amount])
          }
        }
      }

      const weekDays = ['Montag', 'Dienstag', 'Mittwoch', 'Donnerstag']
      const daysUserResp = await api.getWeekDaysForUserName(vm.$keycloak.idTokenParsed.preferred_username)
      vm.chartDataDays.push(['Day', 'Anzahl'])
      for (let i = 0; i < weekDays.length; i++) {
        for (let j = 0; j < daysUserResp.data.length; j++) {
          if (weekDays[i] === daysUserResp.data[j].weekday) {
            vm.chartDataDays.push([weekDays[i], daysUserResp.data[j].amount])
          }
        }
      }
    })
  }
}
</script>

<style scoped lang="scss">
</style>
