import React, { useState } from 'react';
import "./Solver.css"

const Solver = () => {
    const [suggestionAwal, setSuggestionsAwal] = useState([]);
    const [suggestionAkhir, setSuggestionsAkhir] = useState([]);
    const [startInput, setStartInput] = useState("");
    const [destInput, setDestInput] = useState("");
    const [showResultPage, setShowResultPage] = useState(false);
    const [resultData, setResultData] = useState({});
    const [wordList, setWordList] = useState([]);

    useEffect(() => {
        // Fetch data from WordList.txt
        fetchWordList();
    }, []);

    const fetchWordList = async () => {
        try {
            const response = await fetch('/api/WordList.txt');
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.text();
            const words = data.split('\n').map(word => word.trim());
            setWordList(words);
        } catch (error) {
            console.error('Fetch error:', error);
        }
    };

    const handleInputAwal = (e) => {
        const query = e.target.value;
        setStartInput(query);

        if (query) {
            const suggestions = wordList.filter(word => word.startsWith(query));
            setSuggestionsAwal(suggestions);
        } else {
            setSuggestionsAwal([]);
        }
    };

    const handleInputAkhir = (e) => {
        const query = e.target.value;
        setDestInput(query);

        if (query) {
            const suggestions = wordList.filter(word => word.startsWith(query));
            setSuggestionsAkhir(suggestions);
        } else {
            setSuggestionsAkhir([]);
        }
    };

    const fetchPath = async () => {
        setLoading(true);
        try {
            const algorithm = algoSwitch ? "ids" : "bfs";
            const startInputFormatted = startInput.replace(/\s/g, "_");
            const destInputFormatted = destInput.replace(/\s/g, "_");
            const response = await fetch(`http://localhost:8080/${algorithm}?start=${encodeURIComponent(startInputFormatted)}&target=${encodeURIComponent(destInputFormatted)}`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            console.log(data.path);
            setResultData(data);
            setLoading(false);
            setShowResultPage(true);
        } catch (error) {
            setLoading(false);
            console.error('Fetch error:', error);
        }
    };

    const ResultPage = () => {
        return (
            <div className="result-box">
                <h2 className="title">Result</h2>
                <p>Duration: {resultData.duration} s </p>
                <p>Path:</p>
                <ul>
                    {resultData.path.map((step, index) => (
                        <li key={index}><a href={`https://en.wikipedia.org/wiki/${step}`}>{step}</a></li>
                    ))}
                </ul>
                <p>Path Depth: {resultData.depth} </p>
                <p>Total Visited Pages: {resultData.totalVisited} </p>
            </div>
        );
    }
      
    const changeColor = () => {
        var button = document.getElementById("raceButton");
        button.classList.add("clicked");
        setTimeout(() => {
            button.classList.remove("clicked");
        }, 200);
    }
    return (
        <>
        <div id="solver" className="solver-page">
            <div className="solver-container">
                <div className="input-box">
                    <h2>Find your words</h2>
                    <div className='input-box'>
                        <input autocomplete="off" type="text" id="startInput" placeholder="Start Word" value={startInput} onChange={handleInputAwal}/>
                        <div className="suggestion">
                            {suggestionAwal.map((suggestion, index) => (
                                <p key={index} onClick={() => { setStartInput(suggestion); setSuggestionsAwal([]); }}>{suggestion}</p>
                            ))}
                        </div>
                    </div>
                    <div className='input-box'>
                        <input autocomplete="off" type="text" id="destInput" placeholder="End Word" value={destInput} onChange={handleInputAkhir} />
                        <div className="suggestion">
                            {suggestionAkhir.map((suggestion, index) => (
                                <p key={index} onClick={() => { setDestInput(suggestion); setSuggestionsAkhir([]); }}>{suggestion}</p>
                            ))}
                        </div>
                    </div>
                    <div className='submit-container'>
                        <button id="solveButton" onClick={() => {fetchPath(); changeColor();}}>solve</button>
                    </div>
                </div>
            </div>
            {showResultPage && <ResultPage />}
        </div>
        <div className="footer">
            <p>© 2024 Tugas Kecil IF2211 Strategi Algoritma</p>
        </div>
        </>
    );
}
export default Solver;