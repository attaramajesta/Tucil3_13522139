// Project.jsx
import React from 'react';
import './Project.css';
import Home from '../../assets/home.png';

const Project = ({ id }) => {
    return (
      <img src={Home} className="home" id={id} alt="Home"/>
    );
};

export default Project;
