package ru.netogy

import java.lang.RuntimeException

interface Item {
    val id: Int
    val text: String?
    val comments: MutableList<Comment>
}

class NoteNotFoundException(text: String) : RuntimeException(text)
class CommentNotFoundException() : RuntimeException()

abstract class Service<T : Item>{
    private var notes = mutableListOf<T>()

    fun add (element: T) : T{
        notes += element
        return notes.last()
    }

    fun createComment (comment: Comment, id: Int): Comment{
       val note = notes.find {it.id == id  } ?: throw NoteNotFoundException("заметка не найдена")
        note.comments += comment
        return comment
    }

    fun deleteNote(id: Int) : Boolean{
        val note = notes.find {it.id == id  } ?: throw NoteNotFoundException("заметка не найдена")
        notes.remove(note)
        return true
    }

    fun deleteComment(id:Int, commentId: Int): Boolean{
        val note = NoteService.getNoteById(id)
        val comment = note.comments.find { it.commentId == commentId } ?: throw CommentNotFoundException()
        comment.deleted = true
        return true

    }

    fun restoreComment(id: Int, commentId: Int) : Boolean{
        val note = NoteService.getNoteById(id)
        val comment = note.comments.find { it.commentId == commentId } ?: throw CommentNotFoundException()
        comment.deleted = false
        return true

    }

    fun noteEdit(id: Int, element: T): Boolean{
        for((index , noteTemp) in notes.withIndex()){
            if(noteTemp.id == id ){
                notes.removeAt(index)
                notes += element
                return true
            }
        }
        return throw NoteNotFoundException("заметка не найдена")
    }

    fun commentEdit (id: Int,  comment: Comment) : Boolean {
        val note = getNoteById(id)
        for ((index, commentTemp) in  note.comments.withIndex()){
            if (commentTemp.commentId == comment.commentId){
                note.comments.removeAt(index)
                note.comments += comment
                return true
            }
        }
        return throw CommentNotFoundException()
    }

    fun getNotes(): MutableList<T> {
       return notes
    }

    fun getNoteById (id: Int) : T {
        return notes.find {it.id == id  } ?: throw NoteNotFoundException("заметка не найдена")
    }

    fun getCommentsByNoteId (id: Int) : MutableList<Comment> {
        val commentsList: MutableList<Comment> = mutableListOf()
        val note = notes.find {it.id == id  } ?: throw NoteNotFoundException("заметка не найдена")
        for (comment in note.comments) {
            if (!comment.deleted) commentsList.add(comment)
        }
        return commentsList
    }
}



object NoteService : Service<Note>()

data class Note(
    override val id: Int,
    override val text: String? = null,
    override val comments: MutableList<Comment> = mutableListOf()
) : Item

 data class Comment (
     val noteId: Int,
     val commentId: Int,
     var deleted: Boolean = false
 )

fun main(){
    val note = Note(1)
    val note2 = Note(2)
    NoteService.add(note)
    NoteService.add(note2)
    println(NoteService.getNotes())
    NoteService.noteEdit(3, note)
}
