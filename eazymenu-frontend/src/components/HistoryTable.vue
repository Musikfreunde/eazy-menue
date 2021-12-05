<template lang="html">

  <section class="history-table">

    <div class="searchContainer">
      <b-form-input id="searchInput" :placeholder="'Suche: '+selectedFilter" v-model="searchInput" ></b-form-input>
      <b-dropdown id="filter" :text="'Filter: '+selectedFilter" class="filter" >
        <b-dropdown-item v-for="filter in filters" :key="filter" @click="selectedFilter=filter" >{{filter}}</b-dropdown-item>
      </b-dropdown>
    </div>
    <div class="tableContainer">
      <b-table
        id="contentTable"
        hover
        selectable
        select-mode="single"
        :items="filterHistoryByTerm"
        @row-selected="onRowSelected"
        @row-clicked="rowClicked"
        :fields="fields"
        :responsive="true"
        label-sort-asc=""
        label-sort-clear=""
        label-sort-desc=""
        per-page="8"
        :current-page="currentPage"
        ref="contTable"
      >
        <template  #head(createdAt)="">
          <span>Erstellt am, um</span>
        </template>
        <template  #head(menueName)="">
          <span>Menüname</span>
        </template>
        <template #cell(Menüdatum)="data">
          <span><strong>{{new Date (data.item.menueDate).toLocaleDateString('de-DE', { weekday: 'short', year: 'numeric', month: 'short', day: 'numeric' })}}</strong></span>
        </template>
        <template  #head(timeWindow)="">
          <span>Zeit</span>
        </template>
        <template #cell(timeWindow)="data">
          <span><strong>{{data.item.timeWindow}}</strong></span>
        </template>
      </b-table>

      <b-pagination
        v-model="currentPage"
        :total-rows="filterHistoryByTerm.length"
        per-page="8"
        aria-controls="contentTable"
        id="paginator"
      ></b-pagination>
    </div>

    <b-button @click="stornoOrder" class="stornoBtn" pill variant="danger" :disabled="stornoDisabled"><strong><b-icon icon="x-circle-fill" ></b-icon> Storno</strong></b-button>
  </section>

</template>

<script lang="js">
import BestellungUserDto from '../model/BestellungUserDto'
import { api } from '../api'

export default {
  name: 'history-table',
  props: [],
  data () {
    return {
      fields: [
        { key: 'Menüdatum' },
        { key: 'menueName' },
        { key: 'timeWindow' },
        { key: 'createdAt' }
      ],
      stornoDisabled: true,
      currentPage: 1,
      filters: ['Name', 'Menüdatum'],
      selectedFilter: 'Name',
      searchInput: '',
      filteredList: [],
      currentRow: BestellungUserDto
    }
  },
  methods: {
    onRowSelected (items) {
      if (items[0] !== undefined) {
        this.currentRow = items
        this.stornoDisabled = new Date() >= new Date(items[0].menueDate).setHours(9, 0, 0, 0)
      }
    },
    async stornoOrder () {
      this.onRowSelected(this.currentRow)
      if (!this.stornoDisabled) {
        await api.putStornoOrder(this.currentRow[0].id)
        const response = await api.getUserOrders(this.$keycloak.idTokenParsed.preferred_username)
        await this.$store.commit('setUserOrders', response.data)
      } else {
        alert('Stornierung nicht mehr möglich!')
      }
      this.stornoDisabled = true
    },
    rowClicked (items) {
      if (this.currentRow[0] !== undefined) {
        if (this.currentRow[0].id === items.id) {
          this.currentRow = []
          this.stornoDisabled = true
        }
      }
    }
  },
  computed: {
    // eslint-disable-next-line vue/return-in-computed-property
    filterHistoryByTerm: function () {
      if (this.searchInput !== '') {
        if (this.selectedFilter === 'Name') {
          return this.$store.getters.getUserOrders.filter(o => o.menueName.toUpperCase().includes(this.searchInput.toUpperCase()))
        } else if (this.selectedFilter === 'Menüdatum') {
          return this.$store.getters.getUserOrders
            .filter(o => new Date(o.menueDate)
              .toLocaleDateString('de-DE', { weekday: 'short', year: 'numeric', month: 'short', day: 'numeric' })
              .includes(this.searchInput))
        }
      } else {
        return this.$store.getters.getUserOrders
      }
    }
  }
}

</script>

<style scoped lang="scss">
  .history-table {

  }
  .searchContainer{
    display: block;
    margin-top: 1%;
  }
  #searchInput{
    width: 40%;
    float: left;
    margin-left: 1%;
  }
  #filter{
    margin-left: 1%;
    margin-bottom: 1%;
  }

  .stornoBtn{
    height: 50px;
    width: 30%;
    font-size: medium;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: auto;
    margin-right: auto;
    text-align: center;
    margin-bottom: 2%;
  }
  #paginator{
    margin-left: auto;
    margin-right: auto;
    display: flex;
    flex-direction: row;
    justify-content: center;
  }
</style>
