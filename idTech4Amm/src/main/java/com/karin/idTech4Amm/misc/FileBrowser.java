package com.karin.idTech4Amm.misc;

import android.support.v4.provider.DocumentFile;
import android.util.Log;

import com.karin.idTech4Amm.lib.FileUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FileBrowser
{
    public static final int ID_ORDER_BY_NAME = 1;
    public static final int ID_ORDER_BY_TIME = 2;

    public static final int ID_SEQUENCE_ASC = 1;
    public static final int ID_SEQUENCE_DESC = 2;

    public static final int ID_FILTER_FILE = 1;
    public static final int ID_FILTER_DIRECTORY = 1 << 1;

    private String m_currentPath;
    private final Set<String> m_history = new LinkedHashSet<>();
    private final List<FileBrowser.FileModel> m_fileList = new ArrayList<>();
    private int m_sequence = ID_SEQUENCE_ASC;
    private int m_filter = 0;
    private int m_order = ID_ORDER_BY_NAME;
    private List<String> m_extensions = new ArrayList<>();
    private boolean m_showHidden = true;
    private boolean m_ignoreDotDot = false;
    private boolean m_dirNameWithSeparator = true;

    public FileBrowser()
    {
    }

    public FileBrowser(String path)
    {
        if (path != null && !path.isEmpty())
            SetCurrentPath(path);
    }

    protected boolean ListFiles(String path)
    {
        File dir;
        File[] files;
        FileBrowser.FileModel item;

        if (path == null || path.isEmpty())
            return false;

        dir = new File(path);
        if (!dir.isDirectory())
            return false;

        files = dir.listFiles();
        if (files == null)
        {
            if (!path.equals(m_currentPath))
            {
                m_currentPath = path;
                SetupEmptyList(path);
            }
            return false;
        }

        m_fileList.clear();

        for (File f : files)
        {
            String name = f.getName();
            if (".".equals(name))
                continue;
            if(m_filter != 0)
            {
                if((m_filter & ID_FILTER_FILE) == 0 && !f.isDirectory())
                    continue;
                if((m_filter & ID_FILTER_DIRECTORY) == 0 && f.isDirectory())
                    continue;
            }
            if(!m_showHidden && f.isHidden())
                continue;

            if (f.isDirectory() && m_dirNameWithSeparator)
                name += File.separator;

            item = new FileBrowser.FileModel();
            item.name = name;
            item.path = f.getAbsolutePath();
            item.size = f.length();
            item.time = f.lastModified();
            item.type = f.isDirectory() ? FileBrowser.FileModel.ID_FILE_TYPE_DIRECTORY : FileBrowser.FileModel.ID_FILE_TYPE_FILE;
            m_fileList.add(item);
        }

        Collections.sort(m_fileList, m_fileComparator);
        //m_fileList.sort(m_fileComparator);

        // add parent directory
        if (!m_ignoreDotDot && !"/".equals(path))
        {
            item = new FileBrowser.FileModel();
            item.name = "../";
            item.path = dir.getParent();
            item.size = dir.length();
            item.time = dir.lastModified();
            item.type = dir.isDirectory() ? FileBrowser.FileModel.ID_FILE_TYPE_DIRECTORY : FileBrowser.FileModel.ID_FILE_TYPE_FILE;
            m_fileList.add(0, item);
        }
        m_currentPath = path;

        return true;
    }

    public boolean SetCurrentPath(String path)
    {
        if (path != null && !path.equals(m_currentPath))
        {
            if (ListFiles(path))
            {
                //m_currentPath = path;
                m_history.add(m_currentPath);
                return true;
            }
        }
        return false;
    }

    public void Rescan()
    {
        m_fileList.clear();
        ListFiles(m_currentPath);
    }

    public String CurrentPath()
    {
        return m_currentPath;
    }

    public List<FileBrowser.FileModel> FileList()
    {
        return m_fileList;
    }

    public FileBrowser.FileModel GetFileModel(int index)
    {
        if (index >= m_fileList.size())
            return null;
        return m_fileList.get(index);
    }

    public boolean ShowHidden()
    {
        return m_showHidden;
    }

    public FileBrowser SetShowHidden(boolean showHidden)
    {
        if (m_showHidden != showHidden)
        {
            this.m_showHidden = showHidden;
            ListFiles(m_currentPath);
        }
        return this;
    }

    public FileBrowser SetDirNameWithSeparator(boolean dirNameWithSeparator)
    {
        if (m_dirNameWithSeparator != dirNameWithSeparator)
        {
            this.m_dirNameWithSeparator = dirNameWithSeparator;
            ListFiles(m_currentPath);
        }
        return this;
    }

    public FileBrowser SetIgnoreDotDot(boolean b)
    {
        if (m_ignoreDotDot != b)
        {
            this.m_ignoreDotDot = b;
            ListFiles(m_currentPath);
        }
        return this;
    }

    public FileBrowser SetOrder(int i)
    {
        if (m_order != i)
        {
            this.m_order = i;
            ListFiles(m_currentPath);
        }
        return this;
    }

    public FileBrowser SetFilter(int...filters)
    {
        int filter = 0;
        if(null != filters)
        {
            for (int i : filters)
            {
                filter |= i;
            }
        }
        if (m_filter != filter)
        {
            this.m_filter = filter;
            ListFiles(m_currentPath);
        }
        return this;
    }

    public FileBrowser SetSequence(int i)
    {
        if (m_sequence != i)
        {
            this.m_sequence = i;
            ListFiles(m_currentPath);
        }
        return this;
    }

    public boolean ListVirtualFiles(String path, String[] files)
    {
        FileBrowser.FileModel item;

        if (path == null || path.isEmpty())
            return false;

        if (files == null)
        {
            if (!path.equals(m_currentPath))
            {
                m_currentPath = path;
                SetupEmptyList(path);
            }
            return false;
        }

        m_fileList.clear();

        for (String name : files)
        {
            if (".".equals(name))
                continue;
            boolean isDirectory = name.endsWith("/") || "..".equals(name);
            String p = name.endsWith("/") ? name.substring(0, name.length() - 1) : name;
            if(m_filter != 0)
            {
                if((m_filter & ID_FILTER_FILE) == 0 && !isDirectory)
                    continue;
                if((m_filter & ID_FILTER_DIRECTORY) == 0 && isDirectory)
                    continue;
            }

            if (isDirectory && !m_dirNameWithSeparator)
                name = p;

            item = new FileBrowser.FileModel();
            item.name = name;
            item.path = path + "/" + p;
            item.size = 0;
            item.time = -1;
            item.type = isDirectory ? FileBrowser.FileModel.ID_FILE_TYPE_DIRECTORY : FileBrowser.FileModel.ID_FILE_TYPE_FILE;
            m_fileList.add(item);
        }

        Collections.sort(m_fileList, m_fileComparator);
        //m_fileList.sort(m_fileComparator);

        // add parent directory
        if (!m_ignoreDotDot && !"/".equals(path))
        {
            item = new FileBrowser.FileModel();
            item.name = "../";
            item.path = FileUtility.ParentPath(path);
            item.size = 0;
            item.time = -1;
            item.type = FileBrowser.FileModel.ID_FILE_TYPE_DIRECTORY;
            m_fileList.add(0, item);
        }

        m_currentPath = path;
        return true;
    }

    public boolean ListGivenFiles(String path, String[] files)
    {
        File dir;
        FileBrowser.FileModel item;

        if (path == null || path.isEmpty())
            return false;

        dir = new File(path);
        if (!dir.isDirectory())
            return false;

        if (files == null)
        {
            if (!path.equals(m_currentPath))
            {
                m_currentPath = path;
                SetupEmptyList(path);
            }
            return false;
        }

        m_fileList.clear();

        for (String name : files)
        {
            if (".".equals(name))
                continue;

            String p = name.endsWith("/") ? name.substring(0, name.length() - 1) : name;
            File f = new File(path + "/" + p);
            if(!f.exists())
                continue;

            name = f.getName();
            boolean isDirectory = f.isDirectory();
            if(m_filter != 0)
            {
                if((m_filter & ID_FILTER_FILE) == 0 && !isDirectory)
                    continue;
                if((m_filter & ID_FILTER_DIRECTORY) == 0 && isDirectory)
                    continue;
            }
            if(!m_showHidden && f.isHidden())
                continue;

            if (isDirectory && !m_dirNameWithSeparator)
                name += File.separator;

            item = new FileBrowser.FileModel();
            item.name = name;
            item.path = f.getAbsolutePath();
            item.size = f.length();
            item.time = f.lastModified();
            item.type = isDirectory ? FileBrowser.FileModel.ID_FILE_TYPE_DIRECTORY : FileBrowser.FileModel.ID_FILE_TYPE_FILE;
            m_fileList.add(item);
        }

        Collections.sort(m_fileList, m_fileComparator);
        //m_fileList.sort(m_fileComparator);

        // add parent directory
        if (!m_ignoreDotDot && !"/".equals(path))
        {
            item = new FileBrowser.FileModel();
            item.name = "../";
            item.path = dir.getParent();
            item.size = dir.length();
            item.time = dir.lastModified();
            item.type = dir.isDirectory() ? FileBrowser.FileModel.ID_FILE_TYPE_DIRECTORY : FileBrowser.FileModel.ID_FILE_TYPE_FILE;
            m_fileList.add(0, item);
        }

        m_currentPath = path;
        return true;
    }

    public boolean ListDocumentFiles(String path, DocumentFile dir)
    {
        DocumentFile[] files;
        FileBrowser.FileModel item;

        if (path == null || path.isEmpty())
            return false;

        if (dir == null)
        {
            if (!path.equals(m_currentPath))
            {
                m_currentPath = path;
                SetupEmptyList(path);
            }
            return false;
        }

        if (!dir.isDirectory())
            return false;

        files = dir.listFiles();

        m_fileList.clear();

        for (DocumentFile f : files)
        {
            String name = f.getName();
            if (".".equals(name))
                continue;
            if(m_filter != 0)
            {
                if((m_filter & ID_FILTER_FILE) == 0 && !f.isDirectory())
                    continue;
                if((m_filter & ID_FILTER_DIRECTORY) == 0 && f.isDirectory())
                    continue;
            }
            if(!m_showHidden && name.startsWith("."))
                continue;

            if (f.isDirectory() && m_dirNameWithSeparator)
                name += File.separator;

            item = new FileBrowser.FileModel();
            item.name = name;
            item.path = path + "/" + f.getName();
            item.size = f.length();
            item.time = f.lastModified();
            item.type = f.isDirectory() ? FileBrowser.FileModel.ID_FILE_TYPE_DIRECTORY : FileBrowser.FileModel.ID_FILE_TYPE_FILE;
            m_fileList.add(item);
        }

        Collections.sort(m_fileList, m_fileComparator);
        //m_fileList.sort(m_fileComparator);

        // add parent directory
        if (!m_ignoreDotDot && !"/".equals(path))
        {
            item = new FileBrowser.FileModel();
            item.name = "../";
            item.path = FileUtility.ParentPath(path);
            item.size = dir.length();
            item.time = dir.lastModified();
            item.type = dir.isDirectory() ? FileBrowser.FileModel.ID_FILE_TYPE_DIRECTORY : FileBrowser.FileModel.ID_FILE_TYPE_FILE;
            m_fileList.add(0, item);
        }
        m_currentPath = path;

        return true;
    }

    private void SetupEmptyList(String path)
    {
        FileBrowser.FileModel item;

        m_fileList.clear();
        if (!m_ignoreDotDot && !"/".equals(path))
        {
            item = new FileBrowser.FileModel();
            item.name = "../";
            item.path = FileUtility.ParentPath(path);
            item.size = 0;
            item.time = -1;
            item.type = FileBrowser.FileModel.ID_FILE_TYPE_DIRECTORY;
            m_fileList.add(0, item);
        }
    }

    public static class FileModel
    {
        public static final int ID_FILE_TYPE_FILE = 0;
        public static final int ID_FILE_TYPE_DIRECTORY = 1;
        public static final int ID_FILE_TYPE_SYMBOL = 2;

        public String path;
        public String name;
        public long size;
        public int type;
        public long time;
        public String permission;

        public boolean IsDirectory()
        {
            return type == ID_FILE_TYPE_DIRECTORY;
        }
    }

    private final Comparator<FileBrowser.FileModel> m_fileComparator = new Comparator<FileBrowser.FileModel>()
    {
        @Override
        public int compare(FileBrowser.FileModel a, FileBrowser.FileModel b)
        {
            if ("./".equals(a.name))
                return -1;
            if ("../".equals(a.name))
                return -1;
            if (a.type != b.type)
            {
                if (a.type == FileBrowser.FileModel.ID_FILE_TYPE_DIRECTORY)
                    return -1;
                if (b.type == FileBrowser.FileModel.ID_FILE_TYPE_DIRECTORY)
                    return 1;
            }

            int res = 0;
            if (m_order == ID_ORDER_BY_TIME)
                res = Long.signum(a.time - b.time);
            else if (m_order == ID_ORDER_BY_NAME)
                res = a.name.compareToIgnoreCase(b.name);

            if (m_sequence == ID_SEQUENCE_DESC)
                res = -res;

            return res;
        }
    };
}
