// About.jsx
import React from 'react';
import AboutImg from '../../assets/about.png';
import './About.css';

const About = ({ id }) => {
    return (
        <div className='about' id={id}>
            <img src={AboutImg} className="about-word" alt="About"/>
        </div>
    );
};

export default About;
