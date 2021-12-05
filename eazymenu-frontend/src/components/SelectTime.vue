<template lang="html">

  <section class="select-time">
    <b-table id="timeTable" ref="timeTable" striped hover :items="items" :fields="fields" v-if="!showSpinner" >
      <template #cell(chosen)="data">
        <b-form-checkbox
        id="timeBox"
        v-model="data.value"
        @change="onCheckChange($event,data)"
        :disabled="$store.getters.getMenueTimes.find(t => t.id === data.item.id).freeSeats < 1"
        size="lg"
      >
      </b-form-checkbox>
      </template>
      <template class="auswahl" #head(chosen)="">
        <span>Auswahl</span>
      </template>
      <template class="auswahl" #head(time)="">
        <span>Zeit</span>
      </template>
      <template class="auswahl" #head(freeSeats)="">
        <span>Freie Plätze</span>
      </template>
    </b-table>
    <b-spinner v-if="showSpinner" variant="primary"></b-spinner>
  </section>

</template>

<script lang="js">
import { api } from '../api'

export default {
  name: 'select-time',
  props: [],
  data () {
    return {
      showSpinner: true,
      items: [],
      status: false,
      fields: [
        { key: 'time' },
        { key: 'chosen' },
        { key: 'freeSeats' }
      ]
    }
  },
  async beforeCreate () {
    const response = await api.getOpeningTimes()
    // eslint-disable-next-line no-return-assign
    response.data.forEach(r => r.freeSeats = undefined)
    await this.$store.commit('setMenueTimes', response.data)
    await this.$store.commit('setFreePlaces')
    this.items = await this.$store.getters.getMenueTimes
    setTimeout(() => {
      this.showSpinner = false
    }, 600)
  },
  methods: {
    onCheckChange (event, data) {
      if (event) {
        this.items.forEach(item => {
          if (item.time.localeCompare(data.item.time) === 0) {
            item.chosen = event
          } else {
            // Nötig, dass nur eine Zeit ausgewählt werden kann
            item.chosen = false
          }
        })
      }

      this.$refs.timeTable.refresh()
    }

  },
  computed: {

  }
}

</script>

<style scoped lang="scss">
  #timeTable{
    width: 130%;
    text-align: center;
  }
</style>
