package com.n0n3m4.DIII4A.launcher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.karin.idTech4Amm.R;
import com.karin.idTech4Amm.lib.ContextUtility;
import com.karin.idTech4Amm.lib.FileUtility;
import com.n0n3m4.DIII4A.GameLauncher;
import com.n0n3m4.q3e.Q3ELang;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ExtractPatchResourceFunc extends GameLauncherFunc
{
    private final String[] m_patchResources = {
            "glslprogs.pk4",
            "q4base/sabot_a9.pk4",
            "rivensin/play_original_doom3_level.pk4",
            "darkplaces/id1/PAK0.pak",
            "darkplaces/id1/PAK1.pak",
            "darkplaces/id1/PAK2.pak",
            "darkplaces/id1/PAK3.pak",
            "darkplaces/id1/maps/MAPA1.bsp",
            "darkplaces/id1/maps/MAPA2.bsp",
            "darkplaces/id1/maps/MAPAtreze.bsp",
            "darkplaces/id1/maps/salga.bsp",
            "darkplaces/id1/maps/CARRETA.bsp",
            "darkplaces/id1/maps/copaloco.bsp",
            "darkplaces/id1/maps/Sambodromo.bsp",
            "darkplaces/id1/maps/FADIN4.bsp",
            "darkplaces/id1/maps/start.bsp",
            "darkplaces/id1/maps/GLOBE.bsp",
            "darkplaces/id1/maps/cristopaodeacucar.bsp",
            "darkplaces/id1/maps/cristopaodeacucar2.bsp",
            "darkplaces/id1/maps/edmotta.bsp",
            "darkplaces/id1/maps/FINAL.bsp",
            "darkplaces/id1/maps/ULTIMAFASE.bsp",
            "darkplaces/id1/maps/AMANHA.bsp",
            "darkplaces/id1/maps/niteroi.bsp",
            "darkplaces/id1/maps/varginhao.bsp",
            "darkplaces/id1/autoexec.cfg",
            "darkplaces/id1/config.cfg",
            "darkplaces/id1/EpiQuake_MusicPak.pk3",
            "darkplaces/id1/Music/track02.ogg",
            "darkplaces/id1/Music/track03.ogg",
            "darkplaces/id1/Music/track04.ogg",
            "darkplaces/id1/Music/track05.ogg",
            "darkplaces/id1/Music/track06.ogg",
            "darkplaces/id1/Music/track07.ogg",
            "darkplaces/id1/Music/track08.ogg",
            "darkplaces/id1/Music/track09.ogg",
            "darkplaces/id1/Music/track10.ogg",
            "darkplaces/id1/Music/track11.ogg",
            "darkplaces/id1/Music/track12.ogg",
            "darkplaces/id1/Music/track13.ogg",
            "darkplaces/id1/Music/track14.ogg",
            "darkplaces/id1/Music/track15.ogg",
            "darkplaces/id1/Music/track16.ogg",
            "darkplaces/id1/Music/track17.ogg",
            "darkplaces/id1/Music/track18.ogg",





    };
    private String m_path;
    private List<String> m_files;
    private final int m_code;

    public ExtractPatchResourceFunc(GameLauncher gameLauncher, int code)
    {
        super(gameLauncher);
        m_code = code;
    }

    public void Reset()
    {
        if(null != m_files)
            m_files.clear();
        else
            m_files = new ArrayList<>();
    }

    public void Start(Bundle data)
    {
        super.Start(data);
        Reset();

        m_path = data.getString("path");
        // D3-format fonts don't need on longer
        final String[] Names = {
                Q3ELang.tr(m_gameLauncher, R.string.opengles_shader),
                Q3ELang.tr(m_gameLauncher, R.string.bot_q3_bot_support_in_mp_game),
                Q3ELang.tr(m_gameLauncher, R.string.rivensin_play_original_doom3_level),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install2),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install3),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install4),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install5),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install6),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install7),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install8),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install9),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install10),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install11),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install12),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install13),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install14),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install15),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install16),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install17),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install18),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install19),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install20),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install21),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install22),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install23),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install24),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install25),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install26),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install27),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install28),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install29),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install30),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install31),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install32),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install33),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install34),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install35),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install36),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install37),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install38),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install39),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install40),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install41),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install42),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install45),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install46),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install47),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install48),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install49),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install50),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install51),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install52),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install53),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install54),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install55),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install54),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install55),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install56),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install57),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install58),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install59),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install60),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install61),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install62),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install63),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install64),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install65),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install66),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install67),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install68),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install69),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install70),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install71),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install72),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install73),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install74),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install76),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install77),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install78),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install79),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install80),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install81),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install82),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install83),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install84),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install85),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install86),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install87),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install88),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install89),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install90),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install91),
                Q3ELang.tr(m_gameLauncher, R.string.darkplaces_install92),

        };
        AlertDialog.Builder builder = new AlertDialog.Builder(m_gameLauncher);
        builder.setTitle(R.string.game_patch_resource)
                .setItems(Names, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int p)
                    {
                        m_files.add(m_patchResources[p]);
                        ExtractPatchResource();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.extract_all, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int p)
                    {
                        m_files.addAll(Arrays.asList(m_patchResources));
                        ExtractPatchResource();
                    }
                })
                .create()
                .show()
        ;
    }

    private int ExtractPatchResource()
    {
        if(null == m_files || m_files.isEmpty())
            return -1;

        int res = ContextUtility.CheckFilePermission(m_gameLauncher, m_code);
        if(res == ContextUtility.CHECK_PERMISSION_RESULT_REJECT)
            Toast_long(Q3ELang.tr(m_gameLauncher, R.string.can_t_s_read_write_external_storage_permission_is_not_granted, Q3ELang.tr(m_gameLauncher, R.string.access_file)));
        if(res != ContextUtility.CHECK_PERMISSION_RESULT_GRANTED)
            return -1;

        run();
        return m_files.size();
    }

    public void run()
    {
        int r = 0;
        String gamePath = m_path;
        final String BasePath = gamePath + File.separator;
        for (String str : m_files)
        {
            File f = new File(str);
            String path = f.getParent();
            if(null == path)
                path = "";
            String name = f.getName();
            String newFileName = "" + FileUtility.GetFileBaseName(name) + "." + FileUtility.GetFileExtension(name);
            boolean ok = ContextUtility.ExtractAsset(m_gameLauncher, "pak/" + str, BasePath + path + File.separator + newFileName);
            if(ok)
                r++;
        }
        Toast_short(Q3ELang.tr(m_gameLauncher, R.string.extract_path_resource_) + r);
    }
}
