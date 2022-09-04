# Copylefted 2022, Mehrdad ghassabi
# All Wrongs are reserved.

from os import path
from setuptools import setup

_descr = ('Solving Electrical Circuit using graph ')

_long_descr = """
Gracc
=======
`Gracc` provides a way to analyze Electrical Circuits
"""

setup(name='Gracc',
      version='0.0',
      description=_descr,
      long_description=_long_descr,
      author='Mehrdad ghassabi',
      url='https://github.com/Mehrdadghassabi/Gracc',
      packages=['Gracc'],
      classifiers=[
          "Programming Language :: Python :: 2.7",
          "Programming Language :: Python :: 3",
          "License :: OSI Approved :: BSD License",
          "Operating System :: OS Independent",
      ],
      keywords="Electrical Circuit Solver",
      install_requires=['networkx',
       'pandas',         
        'numpy',
        'matplotlib',
        'odeintw'])
