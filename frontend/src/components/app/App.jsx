import './App.scss';
import SignUpPage from '../../pages/sign-up-page/sign-up-page';
import ProblemPage from '../../pages/problem-page/problem-page';
import ProblemList from '../problem-list/problem-list';
import Header from '../header/header';

function App() {
  return (
    <div className="App">
      <Header/>
      <div className="content-wrapper">
        {/* <SignUpPage/> */}
        <ProblemPage/>
      </div>
      
		  
    </div>
  );
}

export default App;
