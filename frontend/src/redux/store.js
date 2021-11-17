import { createStore, applyMiddleware, compose } from 'redux';
import reducer from './user/user-reducer';
import logger from 'redux-logger';

const middlewares = [logger];

const store = compose(
    applyMiddleware(...middlewares),
    window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__(),
)(createStore)(reducer);

export default store;