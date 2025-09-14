import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { Provider } from 'react-redux'
import { BrowserRouter, Route, Routes } from "react-router";
import { store } from './app/store';
import Notes from './components/notes/Notes.jsx'
import NoteEditor from './components/noteEditor/NoteEditor.jsx'
import Auth from './components/Auth/Auth.jsx'


createRoot(document.getElementById('root')).render(
  
    <Provider store={store}>
      <BrowserRouter >
        <Routes>
            <Route path="/" element={<App />}>
              <Route 
                path=""
                element={<Notes />}
              />
              <Route
                path="/createNote"
                element={<NoteEditor />}
              />
              <Route
                path="/auth"
                element={<Auth />}
              />
            </Route>
        </Routes>
      </BrowserRouter>
    </Provider>
  ,
)
