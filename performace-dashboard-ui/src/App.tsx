import React, { useRef, useState } from "react";
import "./App.css";
import ChartsDs from "./components/ChartsDs";

function App() {
  const [traceIdInput, setTraceIdInput] = useState("");
  const [shouldRenderCharts, setShouldRenderCharts] = useState(false);

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTraceIdInput(event.target.value);
  };

  const handleClick = () => {
    if (traceIdInput.length > 0) {
      setShouldRenderCharts(true);
    }
  };
  const handleClickReset = () => {
    setShouldRenderCharts(false);
    setTraceIdInput("");
    // Reset input width using inputRef
    if (inputRef.current) {
      inputRef.current.style.width = ""; // Set width to an empty string
    }
  };
  const inputRef = useRef<HTMLInputElement>(null);

  const adjustInputWidth = () => {
    if (inputRef.current) {
      inputRef.current.style.width = inputRef.current.scrollWidth + "px";
    }
  };
  return (
    <div className="App">
      <header>
        <h1>Performance Dashboard</h1>
      </header>
      <div className="input">
        <label htmlFor="traceIds">Enter trace IDs (comma-separated):</label>
        <input
          id="traceIds"
          type="text"
          ref={inputRef}
          value={traceIdInput}
          onChange={handleInputChange}
          onKeyUp={adjustInputWidth} // Update on keyup
        />
        {!shouldRenderCharts && (
          <button onClick={handleClick}>Render Charts</button>
        )}

        {shouldRenderCharts && (
          <button onClick={handleClickReset}>reset</button>
        )}
      </div>
      {shouldRenderCharts && traceIdInput != "" && (
        <ChartsDs traceIds={traceIdInput.split(",")} />
      )}
    </div>
  );
}

export default App;
