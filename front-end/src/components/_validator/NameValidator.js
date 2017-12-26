export const MIN_NAME_LENGTH = 1
export const MAX_NAME_LENGTH = 16
export const MIN_SURNAME_LENGTH = 1
export const MAX_SURNAME_LENGTH = 32
export const NAME_PATTERN = /^[A-ZА-Я]['a-zа-я]+$/

export const NameValidator = (value, component) => {
  return NAME_PATTERN.test(value)
}
