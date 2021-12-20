import axios from 'axios'

export const api = {
  HOST_URL: 'http://localhost:8080/menue',
  async putMenu (menuDto) {
    return axios.put(this.HOST_URL + '/menues', JSON.stringify(menuDto), { headers: { 'Content-Type': 'application/json' } })
  },
  async putStornoOrder (id) {
    return axios.put(this.HOST_URL + '/bestellung?id=' + id)
  },
  async postMenue (menue) {
    return axios.post(this.HOST_URL + '/menues', JSON.stringify(menue), { headers: { 'Content-Type': 'application/json' } })
  },
  async getMenuesForUebersicht () {
    return axios.get(this.HOST_URL + '/menues')
  },
  async postUserOrder (order) {
    return axios.post(this.HOST_URL + '/bestellung', JSON.stringify(order), { headers: { 'Content-Type': 'application/json' } })
  },
  async getOrdersByDay (date) {
    return axios.get(this.HOST_URL + '/bestellung?date=' + date)
  },
  async getUserOrders (userName) {
    return axios.get(this.HOST_URL + '/bestellung/' + userName)
  },
  async getOpeningTimes () {
    return axios.get(this.HOST_URL + '/oeffnungszeiten')
  },
  async getFreePlaceForTime (dateString, timeId) {
    return axios.get(this.HOST_URL + '/oeffnungszeiten/' + timeId + '?date=' + dateString)
  },
  async getRecommendationForDay (dateString, userName) {
    return axios.get(this.HOST_URL + '/menues/recommendation?date=' + dateString + '&name=' + userName)
  },
  async getAllCategories () {
    return axios.get(this.HOST_URL + '/bestellung/categories')
  },
  async getCategoriesForUserName (userName) {
    return axios.get(this.HOST_URL + '/bestellung/categories/' + userName)
  },
  isOnSameDay (date1, date2) {
    date1.setHours(0, 0, 0, 0)
    date2.setHours(0, 0, 0, 0)
    return date1.toDateString() === date2.toDateString()
  },
  convertEUStringToDate (euDate) {
    const splitted = euDate.split('.')
    return new Date(splitted[2], splitted[1] - 1, splitted[0])
  }
}
