<template lang="html">

  <section class="bestellung">

    <b-container class="formGrid">
      <b-row>
        <b-col>
          <b-form id="formGroup" @submit="onSubmit" v-if="show">
            <b-form-group
              id="erstellerGroup"
              label="Ersteller"
              label-for="erstellerInput"
            >
              <b-form-input
                id="erstellerInput"
                disabled
                v-model="form.ersteller"
                required
              ></b-form-input>
            </b-form-group>

            <b-form-group id="dateGroup" label="Menü am:" label-for="dateInput">
              <b-form-input
                id="dateInput"
                disabled
                v-model="form.date"
                required
              ></b-form-input>
            </b-form-group>

            <b-form-group id="gewaehltGroup" label="Menü:" label-for="gewaehlt">
              <b-form-input
                id="gewaehlt"
                disabled
                v-model="form.menueName"
                required
              ></b-form-input>
            </b-form-group>

            <b-form-group id="anzahlGroup" label="Anzahl:" label-for="anzahl">

              <b-button id="minusBtn" pill @click="buttonClicked($event)">
                <b-icon style="pointer-events: none;" icon="dash"></b-icon>
              </b-button>
              <b-form-input
                id="anzahl"
                disabled
                v-model="form.anzahl"
                required
              ></b-form-input>

              <b-button id="plusBtn" pill v-on:click="buttonClicked($event)">
                <b-icon style="pointer-events: none;" disabled icon="plus"></b-icon>
              </b-button>
            </b-form-group>

            <b-form-group
              id="fuerGroup"
              label="Für wen?"
              label-for="fuerInput"
            >
              <b-form-input
                id="fuerInput"
                v-model="form.fuerWen"
                required
              ></b-form-input>
            </b-form-group>

            <b-form-group
              id="kommentarGroup"
              label="Kommentar: "
              label-for="fuerInput"
            >
              <b-form-textarea
                id="kommentarInput"
                v-model="form.kommentar"
                placeholder="Dein Kommentar ist uns wichtig!"
                rows="3"
                max-rows="6"
              ></b-form-textarea>
            </b-form-group>
          </b-form>

        </b-col>
        <b-col id="rightGridTile">
          <select-time id="selectTime" v-if="this.$store.getters.getCurrentMenues.length !== 0"/>
        </b-col>
      </b-row>

      <div class="buttons">
        <b-button id="abschließen" @click="onSubmit" variant="success" >Abschließen</b-button>
        <b-button id="reset" variant="danger" @click="onQuit"  >Abbrechen</b-button>
      </div>

    </b-container>
  </section>

</template>

<script lang="js">

import SelectTime from '../components/SelectTime'
import { api } from '../api'
import BestellungPostDto from '../model/BestellungPostDto'

export default {
  name: 'bestellung',
  components: {
    SelectTime
  },
  props: [],
  data () {
    return {
      form: {
        ersteller: this.$keycloak.idTokenParsed.preferred_username,
        date: new Date().toDateString(),
        menueName: '',
        anzahl: '1',
        fuerWen: this.$keycloak.idTokenParsed.preferred_username,
        kommentar: ''
      },
      show: true,
      menueId: Number,
      timeIdChosen: Number
    }
  },
  methods: {
    async onSubmit (event) {
      event.preventDefault()
      await this.$store.commit('setFreePlaces')

      if (!this.checkChosen() || this.form.fuerWen.replace(/ /g, '') === '') {
        alert('Formular unvollständig!')
        this.$store.state.menueTimes.forEach(i => {
          i.chosen = false
        })
      } else if (this.$store.getters.getMenueTimes.find(t => t.id === this.timeIdChosen).freeSeats < 1) {
        alert('Keine Plätze mehr für diese Zeit übrig!')
      } else {
        const order = new BestellungPostDto(this.form.ersteller, this.form.anzahl, this.form.kommentar, this.menueId, this.form.fuerWen,
          this.timeIdChosen, this.$keycloak.idTokenParsed.personalnummer)
        await api.postUserOrder(order)
        this.$router.push('/verlauf')
        this.$store.state.menueTimes.forEach(i => {
          i.chosen = false
        })
      }
    },
    onQuit (event) {
      this.$store.state.menueTimes.forEach(i => { i.chosen = false }) // used to uncheck all times after quit
      this.$router.push('/')
    },
    checkChosen () {
      var isChosen = false
      this.$store.state.menueTimes.forEach(i => {
        if (i.chosen) {
          isChosen = true
          this.timeIdChosen = i.id
          return isChosen
        }
      })

      return isChosen
    },
    buttonClicked (event) {
      if (event.target.id === 'plusBtn') {
        this.form.anzahl++
      } else {
        if (this.form.anzahl <= 1) {
          this.form.anzahl = 1
        } else {
          this.form.anzahl--
        }
      }
    }
  },
  async beforeRouteEnter (to, from, next) {
    next(async vm => {
      if (vm.$store.getters.getMenues.length === 0) {
        vm.$router.push('/')
      }
      const menu = await vm.$store.getters.getCurrentMenueById(Number(vm.$route.params.menueId))
      vm.form.date = new Date(menu.date).toLocaleDateString('de-DE', { weekday: 'short', year: 'numeric', month: 'short', day: 'numeric' })
      vm.form.menueName = menu.mainDish
      vm.menueId = menu.id
    })
  },
  async beforeRouteUpdate (to, from, next) {
    if (this.$store.getters.getMenues.length === 0) {
      this.$router.push('/')
    }
    const menu = await this.$store.getters.getCurrentMenueById(this.$route.params.menueId)
    this.form.date = new Date(menu.date).toLocaleDateString('de-DE', { weekday: 'short', year: 'numeric', month: 'short', day: 'numeric' })
    this.form.menueName = menu.mainDish
    this.menueId = menu.id
    next()
  },
  computed: {}
}

</script>

<style scoped lang="scss">
.bestellung {

}

#erstellerInput {
  width: 70%;
  margin-bottom: 1%;
}

#dateInput {
  width: 70%;
  margin-bottom: 1%;

}

#formGroup {
  margin-bottom: 1%;
  width: 140%;
}

#gewaehltGroup {
  width: 70%;
  margin-bottom: 1%;

}

#anzahl {
  display: inline;
  width: 20%;
  text-align: center;
}

#minusBtn {
  display: inline;
  margin-right: 1%;
}

#plusBtn {
  display: inline;

  margin-left: 1%;
}

#fuerGroup {
  width: 30%;
  margin-bottom: 2%;
  margin-top: 1%;

}

#abschließen {
  margin-right: 1.5%;
  margin-left: 2%;
}

.clearfix {
  clear: both
}

.formGrid {
  float: left;
  margin-left: 2%;
  margin-bottom: 1%;
}

#rightGridTile {
  margin-right: 50%;
}

#kommentarInput {
  width: 65%;
  margin-bottom: 1%;
  margin-left: 2%;
}
.buttons{
  margin-top: 2%;
  margin-left: -1.5%;
}
</style>
