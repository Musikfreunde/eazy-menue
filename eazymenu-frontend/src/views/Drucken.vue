<template lang="html">

  <section class="print">
    <b-container id="print-container">
      <b-row id="pickerRow">
        <b-form-datepicker id="example-datepicker" @input="listOrders" v-model="currentDate"
                           value-as-date></b-form-datepicker>
      </b-row>
      <b-row id="buttonRow">
        <b-button id="print-button" @click="printSection" block variant="primary">Drucken</b-button>
      </b-row>
      <b-row>
        <print-orders id="printSection" v-if="showOrders"></print-orders>
      </b-row>
    </b-container>
  </section>

</template>
<script lang="js">
import { api } from '@/api'
import PrintOrders from '@/components/PrintOrders'

export default {
  name: 'print',
  components: { PrintOrders },
  props: [],
  data () {
    return {
      currentDate: new Date(),
      showOrders: false
    }
  },
  async created () {
    this.listOrders()
    const response = await api.getOpeningTimes()
    // eslint-disable-next-line no-return-assign
    response.data.forEach(r => r.freeSeats = undefined)
    await this.$store.commit('setMenueTimes', response.data)
  },
  methods: {
    async listOrders () {
      const response = await api.getOrdersByDay(this.currentDate.toLocaleDateString('pt-br').split('/').reverse().join('-'))
      await this.$store.commit('setKantinenOrders', response.data)
      this.showOrders = true
    },
    printSection () {
      this.$htmlToPaper('printSection')
    }
  },
  computed: {}
}

</script>

<style scoped lang="scss">
#print-button{
  width: 20%;
  margin: 1% auto;
}
#print-container{
  margin-top: 1%;
}
#pickerRow{
  width: 20%;
  margin: auto;
}
</style>
