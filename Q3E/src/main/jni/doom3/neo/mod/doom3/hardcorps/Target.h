/*
===========================================================================

Doom 3 GPL Source Code
Copyright (C) 1999-2011 id Software LLC, a ZeniMax Media company.

This file is part of the Doom 3 GPL Source Code ("Doom 3 Source Code").

Doom 3 Source Code is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Doom 3 Source Code is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Doom 3 Source Code.  If not, see <http://www.gnu.org/licenses/>.

In addition, the Doom 3 Source Code is also subject to certain additional terms. You should have received a copy of these additional terms immediately following the terms and conditions of the GNU General Public License which accompanied the Doom 3 Source Code.  If not, please request a copy in writing from id Software at the address below.

If you have questions concerning this license or the applicable additional terms, you may contact in writing id Software LLC, c/o ZeniMax Media Inc., Suite 120, Rockville, Maryland 20850 USA.

===========================================================================
*/

#ifndef __GAME_TARGET_H__
#define __GAME_TARGET_H__

#include "idlib/math/Interpolate.h"

#include "Entity.h"

/*
===============================================================================

idTarget

===============================================================================
*/

class idTarget : public idEntity {
public:
	CLASS_PROTOTYPE( idTarget );
};


//ivan start
/*
===============================================================================

idTarget_PlayerUtils

===============================================================================
*/

class idTarget_PlayerUtils : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_PlayerUtils );

private:
	// actions - numbers must match the ones in hq2_targets.def! 
	enum {
		PU_ACTION_FREE_CAM	 = 0, //remove
		PU_ACTION_FORCE_CAM	 = 1, //remove
		PU_ACTION_DISTANCE	 = 2, //remove
		PU_ACTION_UNLOCK_PL	 = 3,
		PU_ACTION_LOCK_PL	 = 4,
		PU_ACTION_ADDSCORE	 = 5,
		PU_ACTION_CAM_HEIGHT = 6, //remove
		PU_ACTION_INFOTXT	 = 7
	};

	void				Event_Activate( idEntity *activator );
};

/*
===============================================================================

idTarget_Camera

===============================================================================
*/

class idTarget_Camera : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_Camera );

private:
	void				Event_Activate( idEntity *activator );
};

/*
===============================================================================

idTarget_EnableTargets

===============================================================================
*/

class idTarget_EnableTargets : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_EnableTargets );

						idTarget_EnableTargets( void );
	void				Spawn( void );

	void				Save( idSaveGame *savefile ) const;
	void				Restore( idRestoreGame *savefile );

private:
	bool				toggle;
	bool				enable;
	void				Event_Activate( idEntity *activator );
};


/*
===============================================================================

idTarget_Secret

===============================================================================
*/

class idTarget_Secret : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_Secret );

						idTarget_Secret( void );
	void				Spawn( void );

	void				Save( idSaveGame *savefile ) const;
	void				Restore( idRestoreGame *savefile );
	
private:
	bool				found;
	void				Event_Activate( idEntity *activator );
};


/*
===============================================================================

idTarget_NoLockPath

===============================================================================
*/

class idTarget_NoLockPath : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_NoLockPath );
	
private:
	void				Event_Activate( idEntity *activator );
};

/*
===============================================================================

idTarget_SetPlatPos

===============================================================================
*/

class idTarget_SetPlatPos : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_SetPlatPos );

						idTarget_SetPlatPos( void );

private:
	void				Event_Activate( idEntity *activator );
};


//ivan end

/*
===============================================================================

idTarget_Remove

===============================================================================
*/

class idTarget_Remove : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_Remove );

private:
	void				Event_Activate( idEntity *activator );
};


/*
===============================================================================

idTarget_Show

===============================================================================
*/

class idTarget_Show : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_Show );

private:
	void				Event_Activate( idEntity *activator );
};

/*
===============================================================================

idTarget_Show_Repeat rev 2020 added new type of target entity

===============================================================================
*/

class idTarget_Show_Repeat : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_Show_Repeat );

private:
	void				Event_Activate( idEntity *activator );
};

/*
===============================================================================

idTarget_Hide_Repeat rev 2020 added new type of target entity

===============================================================================
*/

class idTarget_Hide_Repeat : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_Hide_Repeat );

private:
	void				Event_Activate( idEntity *activator );
};

/*
===============================================================================

idTarget_Damage

===============================================================================
*/

class idTarget_Damage : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_Damage );

private:
	void				Event_Activate( idEntity *activator );
};


/*
===============================================================================

idTarget_SessionCommand

===============================================================================
*/

class idTarget_SessionCommand : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_SessionCommand );

private:
	void				Event_Activate( idEntity *activator );
};


/*
===============================================================================

idTarget_EndLevel

===============================================================================
*/

class idTarget_EndLevel : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_EndLevel );

private:
	void				Event_Activate( idEntity *activator );

};


/*
===============================================================================

idTarget_WaitForButton

===============================================================================
*/

class idTarget_WaitForButton : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_WaitForButton );

	void				Think( void );

private:
	void				Event_Activate( idEntity *activator );
};

/*
===============================================================================

idTarget_SetGlobalShaderTime

===============================================================================
*/

class idTarget_SetGlobalShaderTime : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_SetGlobalShaderTime );

private:
	void				Event_Activate( idEntity *activator );
};


/*
===============================================================================

idTarget_SetShaderParm

===============================================================================
*/

class idTarget_SetShaderParm : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_SetShaderParm );

private:
	void				Event_Activate( idEntity *activator );
};


/*
===============================================================================

idTarget_SetShaderTime

===============================================================================
*/

class idTarget_SetShaderTime : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_SetShaderTime );

private:
	void				Event_Activate( idEntity *activator );
};

/*
===============================================================================

idTarget_FadeEntity

===============================================================================
*/

class idTarget_FadeEntity : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_FadeEntity );

						idTarget_FadeEntity( void );

	void				Save( idSaveGame *savefile ) const;
	void				Restore( idRestoreGame *savefile );

	void				Think( void );

private:
	idVec4				fadeFrom;
	int					fadeStart;
	int					fadeEnd;

	void				Event_Activate( idEntity *activator );
};

/*
===============================================================================

idTarget_LightFadeIn

===============================================================================
*/

class idTarget_LightFadeIn : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_LightFadeIn );

private:
	void				Event_Activate( idEntity *activator );
};

/*
===============================================================================

idTarget_LightFadeOut

===============================================================================
*/

class idTarget_LightFadeOut : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_LightFadeOut );

private:
	void				Event_Activate( idEntity *activator );
};

/*
===============================================================================

idTarget_Give

===============================================================================
*/

class idTarget_Give : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_Give );

	void				Spawn( void );

private:
	void				Event_Activate( idEntity *activator );
};


/*
===============================================================================

idTarget_GiveEmail

===============================================================================
*/

class idTarget_GiveEmail : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_GiveEmail );

	void				Spawn( void );

private:
	void				Event_Activate( idEntity *activator );
};

/*
===============================================================================

idTarget_SetModel

===============================================================================
*/

class idTarget_SetModel : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_SetModel );

	void				Spawn( void );

private:
	void				Event_Activate( idEntity *activator );
};


/*
===============================================================================

idTarget_SetInfluence

===============================================================================
*/

class idTarget_SetInfluence : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_SetInfluence );

						idTarget_SetInfluence( void );

	void				Save( idSaveGame *savefile ) const;
	void				Restore( idRestoreGame *savefile );

	void				Spawn( void );

private:
	void				Event_Activate( idEntity *activator );
	void				Event_RestoreInfluence();
	void				Event_GatherEntities();
	void				Event_Flash( float flash, int out );
	void				Event_ClearFlash( float flash );
	void				Think( void );

	idList<int>			lightList;
	idList<int>			guiList;
	idList<int>			soundList;
	idList<int>			genericList;
	float				flashIn;
	float				flashOut;
	float				delay;
	idStr				flashInSound;
	idStr				flashOutSound;
	idEntity *			switchToCamera;
	idInterpolate<float>fovSetting;
	bool				soundFaded;
	bool				restoreOnTrigger;
};


/*
===============================================================================

idTarget_SetKeyVal

===============================================================================
*/

class idTarget_SetKeyVal : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_SetKeyVal );

private:
	void				Event_Activate( idEntity *activator );
};


/*
===============================================================================

idTarget_SetFov

===============================================================================
*/

class idTarget_SetFov : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_SetFov );

	void				Save( idSaveGame *savefile ) const;
	void				Restore( idRestoreGame *savefile );

	void				Think( void );

private:
	idInterpolate<int>	fovSetting;

	void				Event_Activate( idEntity *activator );
};


/*
===============================================================================

idTarget_SetPrimaryObjective

===============================================================================
*/

class idTarget_SetPrimaryObjective : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_SetPrimaryObjective );

private:
	void				Event_Activate( idEntity *activator );
};

/*
===============================================================================

idTarget_LockDoor

===============================================================================
*/

class idTarget_LockDoor: public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_LockDoor );

private:
	void				Event_Activate( idEntity *activator );
};

/*
===============================================================================

idTarget_CallObjectFunction

===============================================================================
*/

class idTarget_CallObjectFunction : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_CallObjectFunction );

private:
	void				Event_Activate( idEntity *activator );
};


/*
===============================================================================

idTarget_LockDoor

===============================================================================
*/

class idTarget_EnableLevelWeapons : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_EnableLevelWeapons );

private:
	void				Event_Activate( idEntity *activator );
};


/*
===============================================================================

idTarget_Tip

===============================================================================
*/

class idTarget_Tip : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_Tip );

						idTarget_Tip( void );

	void				Spawn( void );

	void				Save( idSaveGame *savefile ) const;
	void				Restore( idRestoreGame *savefile );

private:
	idVec3				playerPos;

	void				Event_Activate( idEntity *activator );
	void				Event_TipOff( void );
	void				Event_GetPlayerPos( void );
};

/*
===============================================================================

idTarget_GiveSecurity

===============================================================================
*/
class idTarget_GiveSecurity : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_GiveSecurity );
private:
	void				Event_Activate( idEntity *activator );
};


/*
===============================================================================

idTarget_RemoveWeapons

===============================================================================
*/
class idTarget_RemoveWeapons : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_RemoveWeapons );
private:
	void				Event_Activate( idEntity *activator );
};


/*
===============================================================================

idTarget_LevelTrigger

===============================================================================
*/
class idTarget_LevelTrigger : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_LevelTrigger );
private:
	void				Event_Activate( idEntity *activator );
};

/*
===============================================================================

idTarget_EnableStamina

===============================================================================
*/
class idTarget_EnableStamina : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_EnableStamina );
private:
	void				Event_Activate( idEntity *activator );
};

/*
===============================================================================

idTarget_FadeSoundClass

===============================================================================
*/
class idTarget_FadeSoundClass : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_FadeSoundClass );
private:
	void				Event_Activate( idEntity *activator );
	void				Event_RestoreVolume();
};

//ivan start
/*
===============================================================================

idTarget_StopMusic

===============================================================================
*/
class idTarget_StopMusic : public idTarget {
public:
	CLASS_PROTOTYPE( idTarget_StopMusic );
private:
	void				Event_Activate( idEntity *activator );
};
//ivan end

#endif /* !__GAME_TARGET_H__ */
