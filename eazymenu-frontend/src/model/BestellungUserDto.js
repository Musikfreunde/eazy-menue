export default class BestellungUserDto {
  constructor (id, createdAt, orderedFor, menueName, menueDate) {
    this.id = id
    this.createdAt = createdAt
    this.orderedFor = orderedFor
    this.menueName = menueName
    this.menueDate = menueDate
  }
}
