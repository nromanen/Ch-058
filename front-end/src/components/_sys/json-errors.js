export const NO_ERROR = 0
export const UNEXPECTED = 1
export const ACCESS_DENIED = 2
export const IMAGE_ALREADY_EXISTS = 3
export const MAP_MARKER_ALREADY_EXISTS = 4
export const USER_ALREADY_EXISTS = 5
export const MISSING_PARAMETER = 6
export const BAD_PARAMETER_FORMAT = 7

export function getErrorMessage(error) {
  switch (error.errno) {
    case USER_ALREADY_EXISTS:
      return 'User with the same ' + error.errmsg + ' already exists'
    case MISSING_PARAMETER:
      return 'The field ' + error.errmsg + ' is required'
    case BAD_PARAMETER_FORMAT:
      return 'The field' + error.errmsg + 'has bad format'
    default:
      return error.errmsg
  }
}
