# Neofetch-在终端中显示 Linux 系统信息

https://zhuanlan.zhihu.com/p/69777438

[toc]

Neofetch 是一个简单但有用的命令行系统信息工具，它用 Bash 编写。

Neofetch 和 ScreenFetch 或者 Linux_Logo 很像，但是它可以高度定制，并且还有一些额外的我们要在下面讨论的特点。

它的主要特点有：运行速度快，可以显示全色图像 —— 用 ASCII 字符显示的发行版 logo ，旁边显示系统信息，可以高度定制，可以随时随地显示系统信息，并且在脚本结束的时候还可以通过一个特殊的参数来启用桌面截图。

它会收集有关系统软硬件的信息，并在终端中显示结果。默认情况下，系统信息将与操作系统的 logo 一起显示。但是，你可以进一步地自定义使用 ascii 图像或其他任何图片。

你还可以配置 Neofetch 显示的信息、信息的显示位置和时间。Neofetch 主要用于系统信息的截图。它支持 Linux、BSD、Mac OS X、iOS 和 Windows 操作系统。

在这个简短的教程中，让我们看看如何使用 Neofetch 显示 Linux 系统信息。
![img](https://pic3.zhimg.com/80/v2-2eba8bdd22b6026a69cd535304724bba_1440w.jpg)
## **安装 Neofetch**

Neofetch 可在大多数 Linux 发行版的默认仓库中找到。

在 Arch Linux 及其衍生版上，使用这个命令安装它：

```text
$ sudo pacman -S netofetch
```

在 Debian（Stretch / Sid）上：

```text
$ sudo apt-get install neofetch
```

在 Fedora 27 上：

```text
$ sudo dnf install neofetch
```

在 RHEL、CentOS 上：

启用 EPEL 仓库：

```text
# yum install epel-relase
```

获取 neofetch 仓库：

```text
# curl -o /etc/yum.repos.d/konimex-neofetch-epel-7.repo
https://copr.fedorainfracloud.org/coprs/konimex/neofetch/repo/epel-7/konimex-neofetch-epel-7.repo
```

然后，安装 Neofetch：

```text
# yum install neofetch
```

在 Ubuntu 17.10 和更新版本上：

```text
$ sudo apt-get install neofetch
```

在 Ubuntu 16.10 和更低版本上：

```text
$ sudo add-apt-repository ppa:dawidd0811/neofetch

$ sudo apt update

$ sudo apt install neofetch
```

在 NixOS 上：

```text
$ nix-env -i neofetch
```

## **使用 Neofetch 显示 Linux 系统信息**

Neofetch 非常简单直接。让我们看一些例子。

打开终端，然后运行以下命令：

```text
$ neofetch
```

示例输出：



![img](https://pic3.zhimg.com/80/v2-2eba8bdd22b6026a69cd535304724bba_1440w.jpg)使用 Neofetch 显示 Linux 系统信息



正如你在上面的输出中所看到的，Neofetch 显示了我的 Arch Linux 系统的以下详细信息：

- 已安装操作系统的名称，
- 笔记本型号，
- 内核详细信息，
- 系统运行时间，
- 默认和其他软件包管理器安装的软件数量
- 默认 shell，
- 屏幕分辨率，
- 桌面环境，
- 窗口管理器，
- 窗口管理器的主题，
- 系统主题，
- 系统图标，
- 默认终端，
- CPU 类型，
- GPU 类型，
- 已安装的内存。

Neofetch 还有很多其他选项。我们会看到其中一些。

## **如何在 Neofetch 输出中使用自定义图像？**

默认情况下，Neofetch 将显示你的操作系统 logo 以及系统信息。当然，你可以根据需要更改图像。

要显示图像，Linux 系统应该安装以下依赖项：

1. w3m-img（用于显示图像。w3m-img 有时与 w3m 包捆绑在一起），
2. Imagemagick（用于创建缩略图），
3. 支持 `\033[14t` 或者 xdotool 或者 xwininfo + xprop 或者 xwininfo + xdpyinfo 的终端。

大多数 Linux 发行版的默认仓库中都提供了 W3m-img 和 ImageMagick 包。因此，你可以使用你的发行版的默认包管理器来安装它们。

例如，运行以下命令在 Debian、Ubuntu、Linux Mint 上安装 w3m-img 和 ImageMagick：

```text
$ sudo apt install w3m-img imagemagick
```

以下是带 w3m-img 支持的终端列表：

1. Gnome-terminal，
2. Konsole，
3. st，
4. Terminator，
5. Termite，
6. URxvt，
7. Xfce4-Terminal，
8. Xterm

如果你的系统上已经有了 kitty、Terminology 和 iTerm，那么就无需安装 w3m-img。

现在，运行以下命令以使用自定义图像显示系统信息：

```text
$ neofetch --w3m /home/sk/Pictures/image.png
```

或者，

```text
$ neofetch --w3m --source /home/sk/Pictures/image.png
```

示例输出：



![img](https://pic1.zhimg.com/80/v2-8adc00fb19bad02b0cb613dea6a563ac_1440w.jpg)使用自定义 logo 的 Neofetch 输出



使用你自己的图片替换上面图片的路径。

或者，你可以指向包含以下图像的目录。

```text
$ neofetch --w3m <path-to-directory>
```

## **配置 Neofetch**

当我们第一次运行 Neofetch 时，它默认会为每个用户在 `$HOME/.config/neofetch/config.conf` 中创建一个配置文件。它还会在 `$HOME/.config/neofetch/config` 中创建一个全局的 neofetch 配置文件。你可以调整此文件来告诉 neofetch 该显示、删除和/或修改哪些详细信息。

还可以在不同版本中保留此配置文件。这意味着你只需根据自己的喜好自定义一次，并在升级到更新版本后使用相同的设置。你甚至可以将此文件共享给你的朋友和同事，使他拥有与你相同的设置。

要查看 Neofetch 帮助部分，请运行：

```text
$ neofetch --help
```

就我测试的 Neofetch 而言，它在我的 Arch Linux 系统中完美地工作。它是一个非常方便的工具，可以在终端中轻松快速地打印系统的详细信息。

