// Copyright (C) 2004 Id Software, Inc.
//

#ifndef __SCRIPT_INTERPRETER_H__
#define __SCRIPT_INTERPRETER_H__

#define MAX_STACK_DEPTH 	64
#define LOCALSTACK_SIZE 	(6144 * 2)

typedef struct prstack_s {
	int 				s;
	const function_t	*f;
	int 				stackbase;
} prstack_t;

#ifdef _PREY
class hhThread;
#endif

class idInterpreter {
private:
	prstack_t			callStack[ MAX_STACK_DEPTH ];
	int 				callStackDepth;
	int 				maxStackDepth;

	byte				localstack[ LOCALSTACK_SIZE ];
	int 				localstackUsed;
	int 				localstackBase;
	int 				maxLocalstackUsed;

	const function_t	*currentFunction;
	int 				instructionPointer;

	int					popParms;
	const idEventDef	*multiFrameEvent;
	idEntity			*eventEntity;

	idThread			*thread;

	void		PushVector(const idVec3& vector);
	void				PopParms( int numParms );
	void				PushString( const char *string );
	public://HUMANHEAD: aob - so we can pass parms in manually
	void				Push( intptr_t value );
	private://HUMANHEAD: aob - undo the public declaration
	const char			*FloatToString( float value );
	void				AppendString( idVarDef *def, const char *from );
	void				SetString( idVarDef *def, const char *from );
	const char			*GetString( idVarDef *def );
	varEval_t			GetVariable( idVarDef *def );
	idEntity			*GetEntity( int entnum ) const;
	idScriptObject		*GetScriptObject( int entnum ) const;
	void				NextInstruction( int position );

	void				LeaveFunction( idVarDef *returnDef );
	void				CallEvent( const function_t *func, int argsize );
	void				CallSysEvent( const function_t *func, int argsize );

public:
	bool				doneProcessing;
	bool				threadDying;
	bool				terminateOnExit;
	bool				debug;

						idInterpreter();

	// save games
	void				Save( idSaveGame *savefile ) const;				// archives object for save game file
	void				Restore( idRestoreGame *savefile );				// unarchives object from save game file

	void				SetThread( idThread *pThread );

	void				StackTrace( void ) const;

	int					CurrentLine( void ) const;
	const char			*CurrentFile( void ) const;

	void				Error( char *fmt, ... ) const id_attribute((format(printf,2,3)));
	void				Warning( char *fmt, ... ) const id_attribute((format(printf,2,3)));
	void				DisplayInfo( void ) const;

	bool				BeginMultiFrameEvent( idEntity *ent, const idEventDef *event );
	void				EndMultiFrameEvent( idEntity *ent, const idEventDef *event );
	bool				MultiFrameEventInProgress( void ) const;
	// HUMANHEAD nla - Needed to check what the current event is
	bool				RunningEvent( idEntity *ent, const idEventDef *event );
	// HUMANHEAD END

	void				ThreadCall( idInterpreter *source, const function_t *func, int args );
	void				EnterFunction( const function_t *func, bool clearStack );
	void				EnterObjectFunction( idEntity *self, const function_t *func, bool clearStack );

	bool				Execute( void );
	void				Reset( void );

	bool				GetRegisterValue( const char *name, idStr &out, int scopeDepth );
	int					GetCallstackDepth( void ) const;
	const prstack_t		*GetCallstack( void ) const;
	const function_t	*GetCurrentFunction( void ) const;
	idThread			*GetThread( void ) const;

#ifdef _PREY
	friend class hhThread;
#endif
};

/*
====================
idInterpreter::PopParms
====================
*/
ID_INLINE void idInterpreter::PopParms( int numParms ) {
	// pop our parms off the stack
	if ( localstackUsed < numParms ) {
		Error( "locals stack underflow\n" );
	}

	localstackUsed -= numParms;
}

/*
====================
idInterpreter::Push
====================
*/
ID_INLINE void idInterpreter::Push(intptr_t value ) {
	if ( localstackUsed + sizeof( intptr_t /* int */ ) > LOCALSTACK_SIZE ) {
		Error( "Push: locals stack overflow\n" );
	}
	*(intptr_t*)&localstack[localstackUsed] = value;
	localstackUsed += sizeof(intptr_t);
}

/*
====================
idInterpreter::PushString
====================
*/
ID_INLINE void idInterpreter::PushString( const char *string ) {
	if ( localstackUsed + MAX_STRING_LEN > LOCALSTACK_SIZE ) {
		Error( "PushString: locals stack overflow\n" );
	}
	idStr::Copynz( ( char * )&localstack[ localstackUsed ], string, MAX_STRING_LEN );
	localstackUsed += MAX_STRING_LEN;
}

/*
====================
idInterpreter::FloatToString
====================
*/
ID_INLINE const char *idInterpreter::FloatToString( float value ) {
	static char	text[ 32 ];

	if ( value == ( float )( int )value ) {
		sprintf( text, "%d", ( int )value );
	} else {
		sprintf( text, "%f", value );
	}
	return text;
}

/*
====================
idInterpreter::AppendString
====================
*/
ID_INLINE void idInterpreter::AppendString( idVarDef *def, const char *from ) {
	if ( def->initialized == idVarDef::stackVariable ) {
		idStr::Append( ( char * )&localstack[ localstackBase + def->value.stackOffset ], MAX_STRING_LEN, from );
	} else {
		idStr::Append( def->value.stringPtr, MAX_STRING_LEN, from );
	}
}

/*
====================
idInterpreter::SetString
====================
*/
ID_INLINE void idInterpreter::SetString( idVarDef *def, const char *from ) {
	if ( def->initialized == idVarDef::stackVariable ) {
		idStr::Copynz( ( char * )&localstack[ localstackBase + def->value.stackOffset ], from, MAX_STRING_LEN );
	} else {
		idStr::Copynz( def->value.stringPtr, from, MAX_STRING_LEN );
	}
}

/*
====================
idInterpreter::GetString
====================
*/
ID_INLINE const char *idInterpreter::GetString( idVarDef *def ) {
	if ( def->initialized == idVarDef::stackVariable ) {
		return ( char * )&localstack[ localstackBase + def->value.stackOffset ];
	} else {
		return def->value.stringPtr;
	}
}

/*
====================
idInterpreter::GetVariable
====================
*/
ID_INLINE varEval_t idInterpreter::GetVariable( idVarDef *def ) {
	if ( def->initialized == idVarDef::stackVariable ) {
		varEval_t val;
		val.intPtr = ( int * )&localstack[ localstackBase + def->value.stackOffset ];
		return val;
	} else {
		return def->value;
	}
}

/*
================
idInterpreter::GetEntity
================
*/
ID_INLINE idEntity *idInterpreter::GetEntity( int entnum ) const{
	assert( entnum <= MAX_GENTITIES );
	if ( ( entnum > 0 ) && ( entnum <= MAX_GENTITIES ) ) {
		return gameLocal.entities[ entnum - 1 ];
	}
	return NULL;
}

/*
================
idInterpreter::GetScriptObject
================
*/
ID_INLINE idScriptObject *idInterpreter::GetScriptObject( int entnum ) const {
	idEntity *ent;

	assert( entnum <= MAX_GENTITIES );
	if ( ( entnum > 0 ) && ( entnum <= MAX_GENTITIES ) ) {
		ent = gameLocal.entities[ entnum - 1 ];
		if ( ent && ent->scriptObject.data ) {
			return &ent->scriptObject;
		}
	}
	return NULL;
}

/*
====================
idInterpreter::NextInstruction
====================
*/
ID_INLINE void idInterpreter::NextInstruction( int position ) {
	// Before we execute an instruction, we increment instructionPointer,
	// therefore we need to compensate for that here.
	instructionPointer = position - 1;
}

ID_INLINE void idInterpreter::PushVector(const idVec3& vector)
{
	if (localstackUsed + E_EVENT_SIZEOF_VEC > LOCALSTACK_SIZE)
	{
		Error("Push: locals stack overflow\n");
	}
	*(idVec3*)&localstack[localstackUsed] = vector;
	localstackUsed += E_EVENT_SIZEOF_VEC;
}

#endif /* !__SCRIPT_INTERPRETER_H__ */
