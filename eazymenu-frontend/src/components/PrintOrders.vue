<template lang="html">
  <section class="print-orders">
    <div>
      <b-container class="code-container">
        <b-row>
          <b-col md="1"></b-col>
          <b-col v-for="menu in menueTimes" :key="menu.id"><strong>{{menu.time}}</strong></b-col>
          <b-col><strong>Insgesamt</strong></b-col>
        </b-row>
        <b-row>
          <b-col md="1" class="letterCol"><strong>A:</strong></b-col>
          <b-col v-for="menu in menueTimes" :key="menu.id">{{$store.getters.getCountForTime(menu.time, 'A')}}</b-col>
          <b-col>{{$store.getters.getCountForCode('A')}}</b-col>
        </b-row>
        <b-row>
          <b-col md="1" class="letterCol"><strong>B:</strong></b-col>
          <b-col v-for="menu in menueTimes" :key="menu.id">{{$store.getters.getCountForTime(menu.time, 'B')}}</b-col>
          <b-col>{{$store.getters.getCountForCode('B')}}</b-col>
        </b-row>
        <b-row>
          <b-col md="1" class="letterCol"><strong>C:</strong></b-col>
          <b-col v-for="menu in menueTimes" :key="menu.id">{{$store.getters.getCountForTime(menu.time, 'C')}}</b-col>
          <b-col>{{$store.getters.getCountForCode('C')}}</b-col>
        </b-row>
        <hr>
        <b-row>
          <b-col><strong id="insgesamt">Insgesamt: {{ this.$store.getters.getCountForAll }}</strong></b-col>
        </b-row>

      </b-container>
    </div>
    <div class="table-container">
      <b-table borderless hover :items="items">
        <template #head(orderedFor)="">
          <span>Mitarbeiter</span>
        </template>
        <template #head(date)="">
          <span>Datum</span>
        </template>
        <template #head(timewindow)="">
          <span>Zeit</span>
        </template>
        <template #head(personalNumber)="">
          <span>Persnr.</span>
        </template>
        <template #head(menueCounter)="">
          <span>Anzahl</span>
        </template>
        <template #head(comment)="">
          <span>Kommentar</span>
        </template>
        <template #cell(date)="data">
        <span>{{
            new Date(data.item.date).toLocaleDateString('de-DE', {
              year: 'numeric',
              month: 'numeric',
              day: 'numeric'
            })
          }}</span>
        </template>
      </b-table>
    </div>
  </section>

</template>

<script lang="js">

export default {
  name: 'print-orders',
  props: [],
  data () {
    return {}
  },
  methods: {},
  computed: {
    items: function () {
      if (this.$store.getters.getKantinenOrders.length !== 0) {
        return this.$store.getters.getKantinenOrders
      } else {
        return []
      }
    },
    menueTimes: function () {
      if (this.$store.getters.getMenueTimes.length !== 0) {
        return this.$store.getters.getMenueTimes
      } else {
        return []
      }
    }
  }
}

</script>

<style scoped lang="scss">
.table-container{
  margin-top: 1%;
}
</style>
