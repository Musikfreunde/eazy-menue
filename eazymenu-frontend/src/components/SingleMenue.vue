<template lang="html">

  <section class="single-menue">
    <b-card
      :title="code"
      title-tag="h3"
      img-alt="Image"
      img-top
      tag="article"
      class="mb-2"
      :style="isRecomended ? { 'color': 'green' } : null"
    >
      <img v-if="!isRecomended" src="../assets/food-icon.png" alt="Food Icon">
      <img v-if="isRecomended" src="../assets/food-icon-green.png" alt="Food Icon">
      <b-card-text>
        <b-form-input class="menueNameInput" trim v-model="currentMenue.mainDish" v-if="$keycloak.hasRealmRole('kantine')" @input="updateParent"></b-form-input>
       <a id="mainDishText" :href="getGoogleSearchLink(this.currentMenue.mainDish)" target="_blank" :style="isRecomended ? { 'color': 'green' } : null" v-if="$keycloak.hasRealmRole('mitarbeiter')" ><strong>{{this.currentMenue.mainDish}}</strong></a>
        <b-button v-if="$keycloak.hasRealmRole('kantine')" :id="'popover-target'+code" variant="primary" style="margin-top: 5px">
          Kategorien
        </b-button>
        <b-popover :target="'popover-target'+code" triggers="hover" placement="right">
          <template #title>Kategorien</template>
          <!-- eslint-disable -->
          <ul v-for="category in categories">
            <input :id="category" :value="category" name="product" type="checkbox" v-model="checkedCategories" style="margin-right: 5px" :disabled="$keycloak.hasRealmRole('mitarbeiter')" v-on:change="updateParent"/>
            <label :for="category"><strong>{{category}}</strong></label>
          </ul>
          <!-- eslint-enable -->
        </b-popover>

      </b-card-text>

      <b-button @click="order"
                href="#" variant="primary"
                v-if="$keycloak.hasRealmRole('mitarbeiter')"
                :disabled="!showBestellen"
                style="width: 50%"
                >Bestellen</b-button>
      <b-button v-if="$keycloak.hasRealmRole('mitarbeiter')" :id="'popover-target'+code" variant="primary" style="margin-left: 2%">
        <b-icon icon="question-circle-fill" aria-label="Help"></b-icon>
      </b-button>
    </b-card>
  </section>

</template>

<script lang="js">
import { api } from '../api'
export default {
  name: 'single-menue',
  props: {
    code: String,
    isRecomended: Boolean
  },
  data () {
    return {
      name: '',
      checkedCategories: [],
      categories: this.getAllCategories(),
      currentMenueDate: Date
    }
  },
  methods: {
    mounted () {
      this.getAllCategories()
    },
    getGoogleSearchLink (menuName) {
      return 'https://www.google.com/search?tbm=isch&q=' + menuName
    },
    updateParent () {
      this.currentMenue.categories = this.checkedCategories.join(';')
      console.log(this.currentMenue)
      this.$emit('update:name', this.currentMenue)
    },
    async getAllCategories () {
      const response = await api.getAllCategories()
      this.categories = response.data
    },
    order () {
      if (this.showBestellen) {
        this.$router.push('/bestellung/' + this.currentMenue.id)
      } else {
        alert('Bestellung ungültig!')
      }
    }
  },
  computed: {
    showBestellen: function () {
      if (this.currentMenue !== undefined) {
        const currDate = new Date(this.currentMenue.date)
        const today = new Date()
        return (currDate.setHours(9, 0, 0, 0) >= today)
      }
      return false
    },
    currentMenue: function () {
      // eslint-disable-next-line vue/no-side-effects-in-computed-properties
      if (this.$store.getters.getCurrentMenueForCode(this.code) === undefined) {
        // eslint-disable-next-line vue/no-side-effects-in-computed-properties
        this.$emit('update:name', { mainDish: '' })
        console.log('kein menü für tag')
        return { mainDish: '', categories: [] }
      } else {
        // eslint-disable-next-line
        let menueForCode = this.$store.getters.getCurrentMenueForCode(this.code)

        if (this.currentMenueDate !== undefined && this.currentMenueDate !== menueForCode.date) {
          // eslint-disable-next-line vue/no-side-effects-in-computed-properties
          this.checkedCategories = []
        }

        if (menueForCode !== null) {
          // eslint-disable-next-line vue/no-side-effects-in-computed-properties
          this.currentMenueDate = menueForCode.date
        }

        if (menueForCode !== null && this.checkedCategories.length === 0 && menueForCode.categories !== undefined) {
          // eslint-disable-next-line vue/no-side-effects-in-computed-properties
          this.checkedCategories = menueForCode.categories.split(';')
        }

        menueForCode.categories = this.checkedCategories.join(';')
        this.$emit('update:name', menueForCode)
        // eslint-disable-next-line vue/no-side-effects-in-computed-properties
        return menueForCode
      }
    }
  }
}

</script>

<style scoped lang="scss">
  .single-menue {

  }

  img{
    display: block;
    margin-right: auto;
    margin-left: auto;
    width: 42%;
  }
  .menueNameInput{
    text-align: center;
  }
  #mainDishText{
    font-size: 20px;
    text-decoration: none;
  }
</style>
