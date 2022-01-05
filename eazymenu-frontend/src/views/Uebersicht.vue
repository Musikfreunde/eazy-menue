<template lang="html">

  <section class="uebersicht">

    <b-calendar class="calendar"
                :start-weekday="1"
                :date-disabled-fn="dateDisabled"
                block
                v-model="currentDate"
                locale="de"
                label-selected=""
                label-help=""
                @selected="changeCurrentMenues"></b-calendar>
    <no-menue
      v-if="!$store.getters.getCurrentMenues.length > 0 && this.$keycloak.hasRealmRole('mitarbeiter')"></no-menue>
    <b-container class="menueCardsContainer"
                 v-if="$store.getters.getCurrentMenues.length > 0 || this.$keycloak.hasRealmRole('kantine')">
      <b-row>
        <b-col>
          <b-card class="soupCard">
            <img class="soupImg" src="../assets/soup.png" v-if="$keycloak.hasRealmRole('mitarbeiter')" alt="appetizer image">
            <b-form-group
              id="vorspeise"
              label-for="vorspeise"
              v-if="$keycloak.hasRealmRole('kantine')"
            >
              <img class="soupImg" src="../assets/soup.png" v-if="$keycloak.hasRealmRole('kantine')" alt="appetizer image">
              <b-form-input id="vorspeise" v-model="vorspeise" trim></b-form-input>
            </b-form-group>

            <span class="appetizerAndDessert" v-if="$keycloak.hasRealmRole('mitarbeiter')"><strong>{{ vorspeise }}</strong></span>
          </b-card>
        </b-col>

      </b-row>
      <b-row>
        <b-col>
          <single-menue v-bind:name.sync="menuA" v-if="($store.state.currentMenues.length !== 0
          && $store.getters.getCurrentMenueForCode('A') !== undefined)
          || this.$keycloak.hasRealmRole('kantine')" code="A" :is-recomended="recommendedLetter !== '' && recommendedLetter === 'A'"/>
        </b-col>
        <b-col>
          <single-menue v-bind:name.sync="menuB" v-if="($store.state.currentMenues.length !== 0
          && $store.getters.getCurrentMenueForCode('B') !== undefined)
          || this.$keycloak.hasRealmRole('kantine')" code="B" :is-recomended="recommendedLetter !== '' && recommendedLetter === 'B'"/>
        </b-col>
        <b-col>
          <single-menue v-bind:name.sync="menuC" v-if="($store.state.currentMenues.length !== 0
          && $store.getters.getCurrentMenueForCode('C') !== undefined)
          || this.$keycloak.hasRealmRole('kantine')" code="C" :is-recomended="recommendedLetter !== '' && recommendedLetter === 'C'"/>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <b-card class="dessertCard" footer-tag="footer">
            <img class="dessertImg" src="../assets/dessert.png" v-if="$keycloak.hasRealmRole('mitarbeiter')" alt="dessert image">
            <b-form-group
              id="dessert"
              label-for="dessert"
              v-if="$keycloak.hasRealmRole('kantine')"
            >
              <img class="dessertImg" src="../assets/dessert.png" v-if="$keycloak.hasRealmRole('kantine')" alt="dessert image">
              <b-form-input id="dessert" v-model="dessert" trim></b-form-input>
            </b-form-group>
            <span class="appetizerAndDessert" v-if="$keycloak.hasRealmRole('mitarbeiter')"><strong>{{ dessert }}</strong></span>
            <template #footer v-if="this.$keycloak.hasRealmRole('mitarbeiter')">
              <em>Für Fragen nach in den Speisen enthaltenen Allergenen steht ihnen unser geschultes Küchenfachpersonal
                zu Verfügung.
                <b-button size="sm" variant="primary" class="mb-2"
                          href="http://localhost:8083/img/allergene.d744e667.png" target="_blank">
                  <b-icon icon="question-circle-fill" aria-label="Help"></b-icon>
                  <img  src="../assets/allergene.png" v-show="false" alt="allergene image">
                </b-button>
              </em>
            </template>
          </b-card>
          <div class="buttonsContainer" v-if="$keycloak.hasRealmRole('kantine')">
            <b-button id="speichernBtn" variant="success" @click="saveMenues">Speichern</b-button>
            <b-button id="abbrechenBtn" variant="danger" @click="$router.go()">Abbrechen</b-button>
          </div>
        </b-col>
      </b-row>
    </b-container>
  </section>

</template>

<script>

import SingleMenue from '../components/SingleMenue'
import { api } from '../api'
import NoMenue from '@/components/NoMenue'
import MenuViewDto from '../model/MenuViewDto'

export default {
  name: 'uebersicht',
  components: {
    NoMenue,
    SingleMenue
  },
  props: [],
  data () {
    return {
      currentDate: new Date(),
      vorspeise: '',
      dessert: '',
      menuA: '',
      menuB: '',
      menuC: '',
      recommendedLetter: ''
    }
  },
  methods: {
    dateDisabled (ymd, date) {
      const weekday = date.getDay()
      return weekday === 0 || weekday === 6 || weekday === 5
    },
    getTodaysMenues (selectedDate) {
      return this.$store.getters.getMenues.filter(m => api.isOnSameDay(new Date(m.date), selectedDate))
    },
    async changeCurrentMenues (event) {
      await this.$store.commit('setCurrentMenues', this.getTodaysMenues(new Date(event)))
      if (this.$store.getters.getCurrentMenues.length !== 0) {
        this.currentDate = new Date(this.$store.getters.getCurrentMenues[0].date)
        this.vorspeise = this.$store.getters.getCurrentMenues[0].appetizer
        this.dessert = this.$store.getters.getCurrentMenues[0].dessert

        if (this.$keycloak.hasRealmRole('mitarbeiter')) {
          const resp = await api.getRecommendationForDay(this.$store.getters.getCurrentMenues[0].date, this.$keycloak.idTokenParsed.preferred_username)
          this.recommendedLetter = resp.data
        }
      } else {
        this.vorspeise = ''
        this.dessert = ''
      }
    },
    async saveMenues () {
      let menuePostA
      let menuPostB
      let menuPostC
      if (this.$store.getters.getCurrentMenues.length === 0) {
        menuePostA = new MenuViewDto(null, this.currentDate, 'A', this.vorspeise, this.menuA.mainDish, this.dessert, this.menuA.categories)
        menuPostB = new MenuViewDto(null, this.currentDate, 'B', this.vorspeise, this.menuB.mainDish, this.dessert, this.menuB.categories)
        menuPostC = new MenuViewDto(null, this.currentDate, 'C', this.vorspeise, this.menuC.mainDish, this.dessert, this.menuC.categories)

        await api.postMenue(menuePostA)
        await api.postMenue(menuPostB)
        await api.postMenue(menuPostC)

        alert('Neues Menü erfolgreich hinzugefügt!')

        const response = await api.getMenuesForUebersicht()
        this.$store.commit('setMenues', response.data)
        this.changeCurrentMenues(this.currentDate)
      } else {
        menuePostA = new MenuViewDto(this.menuA.id, this.currentDate, 'A', this.vorspeise, this.menuA.mainDish, this.dessert, this.menuA.categories)
        menuPostB = new MenuViewDto(this.menuB.id, this.currentDate, 'B', this.vorspeise, this.menuB.mainDish, this.dessert, this.menuB.categories)
        menuPostC = new MenuViewDto(this.menuC.id, this.currentDate, 'C', this.vorspeise, this.menuC.mainDish, this.dessert, this.menuC.categories)

        await api.putMenu(menuePostA)
        await api.putMenu(menuPostB)
        await api.putMenu(menuPostC)

        alert('Menüs geändert!')

        const response = await api.getMenuesForUebersicht()
        this.$store.commit('setMenues', response.data)
        this.changeCurrentMenues(this.currentDate)
      }
    }
  },
  async beforeRouteEnter (to, from, next) {
    next(async vm => {
      const response = await api.getMenuesForUebersicht()
      vm.$store.commit('setMenues', response.data)
      if (vm.$store.getters.getMenues.length !== 0) {
        await vm.$store.commit('setCurrentMenues', vm.$store.getters.getMenues.filter(m => api.isOnSameDay(new Date(m.date), new Date())))
        if (vm.$store.getters.getCurrentMenues.length !== 0) {
          vm.currentDate = new Date(vm.$store.getters.getCurrentMenues[0].date)
          vm.vorspeise = vm.$store.getters.getCurrentMenues[0].appetizer
          vm.dessert = vm.$store.getters.getCurrentMenues[0].dessert
          if (vm.$keycloak.hasRealmRole('mitarbeiter')) {
            const resp = await api.getRecommendationForDay(vm.$store.getters.getCurrentMenues[0].date, vm.$keycloak.idTokenParsed.preferred_username)
            vm.recommendedLetter = resp.data
          }
        }
      }
    })
  },
  async beforeRouteUpdate (to, from, next) {
    console.log('update menues!')
    this.changeCurrentMenues()
    next()
  },
  computed: {}
}
</script>

<style scoped lang="scss">
h2{
  color: forestgreen;
  text-align: center;
}
.calendar {
  width: 80%;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 2.5%;
  font-size: x-large;
}

.single-menue {
  text-align: center;
  width: 100%;
}

.soupImg {
  width: 50px;
  padding-right: 10px;
}

.dessertImg {
  width: 50px;
  padding-right: 10px;
}

.soupCard {
  width: 100%;
  margin-bottom: 1%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.dessertCard {
  margin-bottom: 1%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.menueCardsContainer {
  width: 70%;
}

#vorspeise {
  margin-top: 4%;
  text-align: center;
}

#dessert {
  margin-top: 4%;
  text-align: center;
}

.buttonsContainer {
  float: bottom;
  width: 300px;
}

#abbrechenBtn {
  margin-left: 3%;
}
.appetizerAndDessert{
  font-size: 15px;
}
</style>
