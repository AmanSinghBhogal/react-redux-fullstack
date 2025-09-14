import React from 'react';
import './Notes.css';
import Note from '../note/Note';
import { useSelector } from 'react-redux';

const Notes = () => {

    const notes = useSelector(state => state?.notes?.notes);

    const loading = useSelector(state => state?.notes?.isLoading);

    return (
        <>
           {
                loading?
                    "Loading..."
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