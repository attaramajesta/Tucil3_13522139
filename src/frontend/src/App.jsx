import React from 'react';
import './index.css';
import Navbar from "../src/features/navbar/Navbar.jsx";
import Project from "../src/features/project/Project.jsx";
import About from "../src/features/about/About.jsx";

const App = () => {
  return (
    <div className='container'> 
      <Navbar/>
      <Project id='home'/>
      <About id='about'/>
    </div>
  );
}

export default App;
