export default class BestellungPostDto {
  constructor (orderedBy, amount, comment, menueId, orderedFor, timeId, personalNummer) {
    this.orderedBy = orderedBy
    this.amount = amount
    this.comment = comment
    this.menueId = menueId
    this.orderedFor = orderedFor
    this.timeId = timeId
    this.personalNummer = personalNummer
  }
}
