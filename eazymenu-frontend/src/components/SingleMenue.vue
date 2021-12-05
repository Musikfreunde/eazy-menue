<template lang="html">

  <section class="single-menue">
    <b-card
      :title="code"
      title-tag="h3"
      img-alt="Image"
      img-top
      tag="article"
      class="mb-2"
    >
      <img src="../assets/food-icon.png" alt="Food Icon">
      <b-card-text>
        <b-form-input class="menueNameInput" trim v-model="currentMenue.mainDish" v-if="$keycloak.hasRealmRole('kantine')" @input="updateParent"></b-form-input>
       <a id="mainDishText" :href="getGoogleSearchLink(this.currentMenue.mainDish)" target="_blank" v-if="$keycloak.hasRealmRole('mitarbeiter')"><strong>{{this.currentMenue.mainDish}}</strong></a>
      </b-card-text>

      <b-button @click="order"
                href="#" variant="primary"
                v-if="$keycloak.hasRealmRole('mitarbeiter')"
                :disabled="!showBestellen"
                >Bestellen</b-button>
    </b-card>
    <h2 v-if="$keycloak.hasRealmRole('mitarbeiter') && isRecommended === true"></h2>
  </section>

</template>

<script lang="js">

export default {
  name: 'single-menue',
  props: {
    code: String,
    isRecommended: String
  },
  data () {
    return {
      name: ''
    }
  },
  methods: {
    getGoogleSearchLink (menuName) {
      return 'https://www.google.com/search?tbm=isch&q=' + menuName
    },
    updateParent () {
      this.$emit('update:name', this.currentMenue)
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
      if (this.$store.getters.getCurrentMenueForCode(this.code) === undefined) {
        this.$emit('update:name', { mainDish: '' })
        return { mainDish: '' }
      } else {
        const menueForCode = this.$store.getters.getCurrentMenueForCode(this.code)
        this.$emit('update:name', menueForCode)
        return menueForCode
      }
    }
  }
}

</script>

<style scoped lang="scss">
  h2{
    color:green
  }
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
