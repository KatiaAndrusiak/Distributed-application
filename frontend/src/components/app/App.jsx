import './App.scss';
import SignUp from '../sign-up/sign-up';
import Header from '../header/header';
import ProblemList from '../problem-list/problem-list';

function App() {
  return (
    <div className="App">
      <Header/>
      <div className="content-wrapper">
        <SignUp/>
        {/* <ProblemList/> */}
      </div>
      
		  
    </div>
  );
}

export default App;
