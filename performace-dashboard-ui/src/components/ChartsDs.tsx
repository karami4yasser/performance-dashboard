import React, { useEffect, useState } from "react";
import { ChartDataset } from "../typing";
import { getChartDatasets } from "../api/getChartDatasets";
import "./ChartDs.css";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";
import { Doughnut } from "react-chartjs-2";

ChartJS.register(ArcElement, Tooltip, Legend);

type ChartsDsProps = {
  traceIds: string[];
};

function ChartsDs({ traceIds }: ChartsDsProps) {
  const [chartDs, setChartDs] = useState<ChartDataset[]>();
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      setError(null);

      try {
        const results = await getChartDatasets(traceIds);
        if (results.status === 200) {
          console.log(results.data);
          setChartDs(results.data);
        } else {
          setError("Failed to load chart data. Please try again later.");
        }
      } catch (error) {
        console.error("Error fetching chart data:", error);
        setError("An unexpected error occurred. Please try again later.");
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, []);

  if (isLoading) {
    return <div>Loading charts...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  if (!chartDs) {
    return null;
  }

  return (
    <>
      <div className="grid">
        {chartDs &&
          chartDs.map((ds: ChartDataset, index) => (
            <div key={ds.traceId || index} className="chart-item">
              <Doughnut
                data={{
                  labels: ds.labels,
                  datasets: [
                    {
                      label: `Operation duration in ms `,
                      data: ds.durations,
                      backgroundColor: ds.backgroundColor,
                      borderColor: ds.borderColor,
                      borderWidth: 1,
                    },
                  ],
                }}
                options={{
                  spacing: 20,
                  plugins: {
                    legend: {
                      title: {
                        display: true,
                        color: "black",
                        text: `Trace Id ${ds.traceId} took : ${ds.totalDuration} ms  `,
                        font: {
                          size: 15,
                        },
                      },
                      labels: {
                        font: {
                          size: 8,
                        },
                      },
                    },
                  },
                }}
              />
            </div>
          ))}
      </div>
    </>
  );
}

export default ChartsDs;
