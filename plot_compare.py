import plotly.plotly as py
import plotly.graph_objs as go
import plotly.figure_factory as FF
import plotly.io as pio

import numpy as np
import pandas as pd

import os
import glob

extension = 'csv'
result_ga = 'data/ga.csv'
result_ant = 'data/ant.csv'

df_ga = pd.read_csv(result_ga)
df_ant = pd.read_csv(result_ant)

df_ga_external_source = FF.create_table(df_ga.head())
df_ant_external_source = FF.create_table(df_ant.head())

trace_fittest = go.Scatter(x = df_ant['gen'], y = df_ant['best'],
                name='Ant Fittest')

trace_average = go.Scatter(x = df_ant['gen'], y = df_ant['avg'],
                name='Ant Average')

trace_fittest2 = go.Scatter(x = df_ga['gen'], y = df_ga['best'],
                name='GA Fittest')

trace_average2 = go.Scatter(x = df_ga['gen'], y = df_ga['avg'],
                name='GA Average')

layout = go.Layout(
    title='Ant Algorithm',
    plot_bgcolor='rgb(255, 255, 255)',
    autosize=False,
    yaxis={
    'type':'linear',
    'autorange':True,
    'title':'Distance'
    },
    showlegend=True,
    height=400,
    width=600,
    titlefont={
    'size':18
    },
    xaxis={
    'type':'linear',
    'autorange':True,
    'title':'Generation'
    },
    hovermode='closest',
    font={
    'size':12
    },
    margin={
    "pad": 0,
    "r": 0,
    "b": 40,
    "t": 30,
    "l": 50,
    }
)

fig = go.Figure(data=[trace_fittest, trace_average, trace_fittest2, trace_average2], layout=layout)
pio.write_image(fig, 'data/comparison.png')

#py.iplot(fig, filename='Tournament Selection (Elitism 1%) - ' + str(i))
