export default class OpenTimeDto {
  constructor (id, time, chosen, maxSeats, freeSeats, timeWindow) {
    this.id = id
    this.time = time
    this.chosen = chosen
    this.maxSeats = maxSeats
    this.freeSeats = freeSeats
    this.timeWindow = timeWindow
  }
}
