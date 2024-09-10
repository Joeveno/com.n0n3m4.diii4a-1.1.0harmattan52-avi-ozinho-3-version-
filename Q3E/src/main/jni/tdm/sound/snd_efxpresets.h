/*****************************************************************************
The Dark Mod GPL Source Code

This file is part of the The Dark Mod Source Code, originally based
on the Doom 3 GPL Source Code as published in 2011.

The Dark Mod Source Code is free software: you can redistribute it
and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation, either version 3 of the License,
or (at your option) any later version. For details, see LICENSE.TXT.

Project: The Dark Mod (http://www.thedarkmod.com/)

******************************************************************************/

#ifndef __SND_EFXPRESETS__
#define __SND_EFXPRESETS__

#include "snd_local.h"
//stgatilov: if this include causes problem, copy the presets header from OpenAL to sound/ directory
#include <AL/efx-presets.h>

struct EfxPreset {
	const char* name;
	EFXEAXREVERBPROPERTIES props;
};
extern EfxPreset efxPresets[];

#endif
