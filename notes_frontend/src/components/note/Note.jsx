import React from 'react';
import './Note.css';
import { MdOutlineDeleteOutline } from "react-icons/md";
import { GoPencil } from "react-icons/go";
import { useDispatch, useSelector } from 'react-redux';
import noteService from '../../api/NotesService';
import {removeNote, setEditNote} from '../../reducers/notes/NoteSlice';
import { Link} from 'react-router';

const Note = ({ data }) => {

    const dispatch = useDispatch();

    function formatDate(dateString) {
        const date = new Date(dateString);
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are 0-based
        const year = date.getFullYear();
        return `${day}-${month}-${year}`;
    }
    const isAuthenticated = useSelector(state => state.users.isAuthenticated);

    const userId = useSelector(state=> state.users.user.id);

    const userData = useSelector(state => state.users.user)

    function deleteNote(){
        console.log("delete clicked")
        console.log(userData)
        noteService.deleteNote(data, userData)
            .then((response)=> {
                console.log("data:");
                console.log(response?.data?.response?.data)
                dispatch(removeNote(data.id))

            })
            .finally(()=> {
                console.log("Note Deleted Succcessfully")
            })
        
    }

    function updateNote(){
        console.log("Update Clicked");
        dispatch(setEditNote(data));
    }

    const create_date = formatDate(data.created_at);
    const update_date = data.updated_at != null ? formatDate(data.updated_at) : null;
    return (
        <div class="NoteContainer">
            <div class="NoteTitle">
                Title: {data.title}
            </div>
            <div class='NoteContent'>Message: {data.content}</div>
            <div class="NoteCreatedAt">Created: {create_date}</div>
            <div class="NoteUpdatedAt">{update_date != null ? "Last Modified:" + update_date : null}</div>
            {
                (isAuthenticated === true)&& (userId === data.user_id || userId === "a7f0e956-824f-4327-b22e-8886921282ff") ? 
                <>
                    <button onClick={deleteNote}><MdOutlineDeleteOutline /> </button>
                    {
                        userId === "a7f0e956-824f-4327-b22e-8886921282ff"?null: <Link to="/createNote" onClick={updateNote}><GoPencil /> </Link>
                    }
                </>
                
                :
                null
            }

        </div>
    )
}

export default Note