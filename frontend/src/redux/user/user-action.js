import {UserActionTypes} from './user-type';

export const setUser = (user) => {
    return {
        type: UserActionTypes.SET_USER,
        payload: user
    }
}

export const logout = () => {
    return {
        type: UserActionTypes.LOGOUT,
    }
}

export const updateProblems = (problem) => {
    return {
        type: UserActionTypes.UPDATE_PROBLEMS,
        payload: problem
    }
}