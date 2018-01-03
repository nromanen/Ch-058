export const NO_ERROR = 0
export const UNEXPECTED = 1
export const ACCESS_DENIED = 2
export const IMAGE_ALREADY_EXISTS = 3
export const MAP_MARKER_ALREADY_EXISTS = 4
export const USER_ALREADY_EXISTS = 5
export const MISSING_PARAMETER = 6
export const BAD_PARAMETER_FORMAT = 7

// eslint-disable-next-line
export function getErrorMessage(error) {
  switch (error.errno) {
    case NO_ERROR:
      return 'Everything fine'
    case UNEXPECTED:
      return 'Unexpected error'
    case ACCESS_DENIED:
      return 'Access denied'
    case IMAGE_ALREADY_EXISTS:
      return 'Image already exists'
    case MAP_MARKER_ALREADY_EXISTS:
      return 'Map marker already exists'
    case USER_ALREADY_EXISTS:
      return 'User with the same ' + error.field + ' already exists'
    case MISSING_PARAMETER:
      return 'The field ' + error.field + ' is required'
    case BAD_PARAMETER_FORMAT:
      return 'The field ' + error.field + ' has bad format'
    default:
      return error.errmsg
  }
}
