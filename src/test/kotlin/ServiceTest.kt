import org.junit.Test
import org.junit.Assert.*
import ru.netogy.*

class ServiceTest {

    @Test
    fun addNote() {
        val note = Note(1)
        val result = NoteService.add(note)
        assertEquals(note.id, result.id)
    }

    @Test
    fun createComment(){
        val note = Note(1)
        val comment = Comment(1,1)
        NoteService.add(note)
        val result = NoteService.createComment(comment, 1)
        assertEquals(result, comment)
    }

    @Test (expected = NoteNotFoundException::class)
    fun createCommentShouldThrow(){
        val note = Note(1)
        val comment = Comment(1,1)
        NoteService.add(note)
        NoteService.createComment(comment, 2)
    }

    @Test
    fun deleteNote (){
        val note = Note(1)
        NoteService.add(note)
        NoteService.deleteNote(1)
        assert(true)
    }

    @Test(expected = NoteNotFoundException::class)
    fun deleteNoteShouldThrow (){
        val note = Note(1)
        NoteService.add(note)
        NoteService.deleteNote(2)
    }

    @Test
    fun deleteComment(){
        val note = Note(1)
        val comment = Comment(1,1)
        NoteService.add(note)
        NoteService.createComment(comment, 1)
        NoteService.deleteComment(1,1)
        assert(true)
    }

    @Test (expected = CommentNotFoundException::class)
    fun deleteCommentShouldThrow(){
        val note = Note(1)
        val comment = Comment(1,1)
        NoteService.add(note)
        NoteService.createComment(comment, 1)
        NoteService.deleteComment(1,2)
    }

    @Test
    fun restoreComment(){
        val note = Note(1)
        val comment = Comment(1,1)
        NoteService.add(note)
        NoteService.createComment(comment, 1)
        NoteService.deleteComment(1,1)
        NoteService.restoreComment(1,1)
        assert(true)
    }

    @Test (expected = CommentNotFoundException::class)
    fun restoreCommentShouldThrow(){
        val note = Note(1)
        val comment = Comment(1,1)
        NoteService.add(note)
        NoteService.createComment(comment, 1)
        NoteService.deleteComment(1,1)
        NoteService.restoreComment(1,2)
    }

    @Test
    fun noteEdit (){
        val note = Note(1)
        val note2 = Note(2)
        NoteService.add(note)
        NoteService.noteEdit(1, note2)
        assert(true)
    }
    @Test (expected = NoteNotFoundException::class)
    fun noteEditShouldThrow (){
        val note = Note(1)
        val note2 = Note(2)
        NoteService.add(note)
        NoteService.noteEdit(2, note2)
    }

    @Test
    fun commentEdit(){
        val note = Note(1)
        val comment = Comment(1,1)
        val comment2 = Comment(1, 1)
        NoteService.add(note)
        NoteService.createComment(comment, 1)
        NoteService.commentEdit(1, comment2)
        assert(true)
    }

    @Test(expected = CommentNotFoundException::class)
    fun commentEditShouldThrow(){
        val note = Note(1)
        val comment = Comment(1,1)
        val comment2 = Comment(1, 2)
        NoteService.add(note)
        NoteService.createComment(comment, 1)
        NoteService.commentEdit(1, comment2)

    }


    @Test
    fun getNotes(){
        val note = Note(1)
        NoteService.add(note)
        val notesList = NoteService.getNotes()
        val result = notesList[0]
        assertEquals(1,result.id)
    }

    @Test
    fun getNoteById(){
        val note = Note(1)
        NoteService.add(note)
        val result = NoteService.getNoteById(1)
        assertEquals(1, result.id)
    }
    @Test (expected = NoteNotFoundException::class)
    fun getNoteByIdShouldThrow(){
        val note = Note(1)
        NoteService.add(note)
        NoteService.getNoteById(2)
    }

    @Test
    fun getCommentsByNoteId (){
        val note = Note(1)
        val comment = Comment(1,1)
        NoteService.add(note)
        NoteService.createComment(comment,1)
        val commentsList = NoteService.getCommentsByNoteId(1)
        val result = commentsList[0]
        assertEquals(1, result.noteId)

    }

    @Test (expected = NoteNotFoundException::class)
    fun getCommentsByNoteIdShouldThrow(){
        val note = Note(1)
        NoteService.add(note)
        NoteService.getCommentsByNoteId(2)
    }



}