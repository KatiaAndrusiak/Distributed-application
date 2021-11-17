import './App.scss';
import { BrowserRouter as Router, Route, Switch, Redirect } from 'react-router-dom';
import {useSelector} from 'react-redux';

import HomePage from '../../pages/home-page/home-page';
import SignUpPage from '../../pages/sign-up-page/sign-up-page';
import SignInPage from '../../pages/sign-in-page/sign-in-page';
import ProblemPage from '../../pages/problem-page/problem-page';
import Header from '../header/header';


function App() {
	const {isLogged} = useSelector(state => state);

	return (
		<Router>
		<div className="App">
			<Header/>
			<div className="content-wrapper">
				<Switch>
					<Route exact path="/">
						<HomePage/>
					</Route>
					<Route exact path='/signup' render={()=> isLogged ?  (<Redirect to='/'/>) : (<SignUpPage/>)}/> 
					<Route exact path='/signin' render={()=> isLogged ?  (<Redirect to='/'/>) : (<SignInPage/>)}/>
					<Route exact path='/problems' render={()=> !isLogged ?  (<Redirect to='/'/>) : (<ProblemPage/>)}/>
				</Switch>

			</div>
			
			
		</div>
		</Router>

	);
}

export default App;
