import React from 'react';
import './Note.css';

const Note = ({data}) => {

    const create_date = new Date(data.created_at)
    const update_date = data.updated_at != null? new Date(data.updated_at) : null;
    return (
        <div class="NoteContainer">
            <div class="NoteTitle">
                {data.title}
            </div> 
            <div class='NoteContent'>{data.content}</div>
            <div class="NoteCreatedAt">Created: {create_date.getDate()}</div>
            <div class="NoteUpdatedAt">{update_date != null? "Last Modified:"+update_date.getDate() : null}</div>
        </div>
    )
}

export default Note