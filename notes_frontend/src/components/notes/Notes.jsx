import React from 'react';
import './Notes.css';
import Note from '../note/Note';
import {  useSelector } from 'react-redux';

const Notes = ({loading}) => {

    const notes = useSelector(state => state.notes);
    
    return (
        <>
           {
                loading?
                    "Page is Loading"
                    :
                    <div class="NotesContainer">
                        {
                            notes.map((note) => (
                                <Note data={note} key={note.id} />
                            ))
                        }
                    </div>
           
            }
        </>
    )
}

export default Notes;