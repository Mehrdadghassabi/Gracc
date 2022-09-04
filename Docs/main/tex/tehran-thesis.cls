%%
%%  This is file `isfahan-thesis.cls',
%%  It is a modification of tehran-Thesis by sina.momken
%%  which itself is a change of IUST-thesis by Mahmood Amintoosi
%%  which itself is a change of Tabriz_thesis by Vahid Damanafshan
%%  which itself is a change of xepersian-thesis by Vafa Khalighi
%%
%%  Author: mehrdad ghassabi, mehrdad.gv@gmail.com
%%	Base author: sina momken, sina.momken@gmail.com
%%  https://github.com/Mehrdadghassabi/isfahan-thesis
%%
\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{tehran-thesis}
              [05/09/2017 v0.1
 Persian thesis document class in XeLaTeX for University of Tehran projects/theses/dissertations]
\DeclareOption*{\PassOptionsToClass{\CurrentOption}{book}}
\ProcessOptions
\LoadClass[a4paper,12pt]{book}
\def\university#1{\gdef\@university{#1}}
\def\faculty#1{\gdef\@faculty{#1}}
\def\department#1{\gdef\@department{#1}}
\def\degree#1{\gdef\@degree{#1}}
\def\thesisdate#1{\gdef\@thesisdate{#1}}
\newcommand{\firstsupervisor}[1]{\def\@firstsupervisor{#1}}
\newcommand{\firstsupervisorrank}[1]{\def\@firstsupervisorrank{#1}}
\newcommand{\secondsupervisor}[1]{\def\@secondsupervisor{#1}}
\newcommand{\secondsupervisorrank}[1]{\def\@secondsupervisorrank{#1}}
\newcommand{\firstadvisor}[1]{\def\@firstadvisor{#1}}
\newcommand{\firstadvisorrank}[1]{\def\@firstadvisorrank{#1}}
\newcommand{\secondadvisor}[1]{\def\@secondadvisor{#1}}
\newcommand{\secondadvisorrank}[1]{\def\@secondadvisorrank{#1}}
\newcommand{\internaljudge}[1]{\def\@internaljudge{#1}}
\newcommand{\internaljudgerank}[1]{\def\@internaljudgerank{#1}}
\newcommand{\externaljudge}[1]{\def\@externaljudge{#1}}
\newcommand{\externaljudgerank}[1]{\def\@externaljudgerank{#1}}
\newcommand{\externaljudgeuniversity}[1]{\def\@externaljudgeuniversity{#1}}
\newcommand{\graduatedeputy}[1]{\def\@graduatedeputy{#1}}
\newcommand{\graduatedeputyrank}[1]{\def\@graduatedeputyrank{#1}}
\def\name#1{\gdef\@name{#1}}
\def\surname#1{\gdef\@surname{#1}}
\def\dedication#1{\gdef\@dedication{#1}}
\long\def\acknowledgement#1{\gdef\@acknowledgement{#1}}
\def\keywords#1{\gdef\@keywords{#1}}
\long\def\fa-abstract#1{\gdef\@fa-abstract{#1}}
\def\subject#1{\gdef\@subject{#1}}
\def\projectLabel#1{\gdef\@projectLabel{#1}}
\def\studentID#1{\gdef\@studentID{#1}}
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\newif\if@bscthesis
\@bscthesisfalse
\DeclareOption{bsc}{\@bscthesistrue}
\newif\if@mscthesis
\@mscthesisfalse
\DeclareOption{msc}{\@mscthesistrue}
\newif\if@irfonts
\@irfontsfalse
\DeclareOption{irfonts}{\@irfontstrue}
\ProcessOptions

\ifx\@projectLabel\undefined%
{
\if@mscthesis
{\projectLabel{پایان‌نامه}}
\else
 \if@bscthesis
 {\projectLabel{پروژه}}
 \else
{\projectLabel{رساله}}
\fi\fi
}\fi

\ifx\@degree\undefined%
{
\if@bscthesis
\degree{کارشناسی}
}


%%%%%%%%%%%%%%%%%%%%%%%%%%%
\newcommand{\besmPage}{
%% بسم الله الرحمن الرحیم
\cleardoublepage
\thispagestyle{empty}
\hfill
\begin{center}
\includegraphics[width=10cm]{img/besm}
\end{center}
}
%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% فرم داخلی جلد به فارسی (دارای استاد مشاور)
\newcommand{\titlePage}{\begin{titlepage}
%\topmargin=-30pt
\vspace{-10mm}

% آرم
\centerline{{\includegraphics[width=5cm]{logo}}}
\centerline{\bf \@university}
\centerline{\large\bfseries\@faculty}
\centerline{\large\bfseries\@department}
% بعد از آرم‌
\centering
\vskip 1.5cm
{\huge\titlefont\bfseries \@title}\par
\vskip 5mm

\par
{\Large\bfseries{\@name\ \ \@surname}}
\par
\vskip 15mm
\large
{
\ifx\@firstsupervisor\undefined%
	

\else
\large
استاد راهنما
\par
{\Large\bfseries \@firstsupervisor}
\fi\fi}

\vskip 6mm
%\vfill
{\large \@thesisdate}
%\vfill
\end{titlepage}%
} % end of titlePage
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
\def\titlepage{\if@RTL\cleardoublepage\else\clearpage\fi
  \thispagestyle{empty}
  \parindent 0pt \parskip 10pt plus 1fil minus 1fil
  \def\baselinestretch{1}\@normalsize\vbox to \vsize\bgroup\vbox to 9in\bgroup}
\def\endtitlepage{\par\kern 0pt\egroup\vss\egroup}

\def\signature{\vspace{1cm}
\begin{flushleft}
{%\nastaliq \scriptsize 
\@name\ \@surname \\
\@thesisdate}
\end{flushleft}}
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% تاییدیه هیات داوران
\newcommand{\davaranPage}{
\cleardoublepage
%\setcounter{page}{1}
\thispagestyle{empty}

\centerline{{\includegraphics[width=5cm]{logo}}}
\centerline{\bf \@university}
\centerline{\large\bfseries\@faculty}
\centerline{\large\bfseries\@department}


\begin{center}

\end{center}
\vspace{.1cm}
هیأت داوران \@projectLabel{}ٔ \@degree{} آقای~/~خانم \@name{} \@surname{} به شمارهٔ دانشجویی \@studentID{} \\در رشتهٔ \@subject{} را در تاریخ ............ با عنوان  «\@title{}» و نمره نهایی زیر ارزیابی کرد. \\

\makebox[\textwidth][l]{
\parbox{0.7\textwidth}{
\makebox[2cm]{}\hspace{1ex}\makebox[2cm]{به عدد}\makebox[5cm]{به حروف}\\
\makebox[2cm][l]{با نمرهٔ نهایی}\hspace{1ex}\framebox[2cm]{\strut}\framebox[5cm]{\strut}
\\
\\


}
}
\begin{center}
\noindent
	\begin{minipage}{0.45\textwidth}
		\begin{center}
			نام و نام خانوادگی استاد راهنما:\\
			تاریخ و امضا:
		\end{center}
	\end{minipage}
	\hspace{0.08\textwidth}
	\begin{minipage}{0.45\textwidth}
		\begin{center}
			نام و نام خانوادگی استاد داور:\\
			تاریخ و امضا:
		\end{center}
	\end{minipage}
\end{center}

} % End of davaranPage
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% تأییدیه‌ی صحت و اصالت نتایج
\newcommand{\esalatPage}{
\cleardoublepage
\thispagestyle{empty}
\begin{center}
{\Large \titlefont\bfseries
تعهدنامهٔ اصالت اثر\\
}
\vspace{.5cm}
باسمه تعالی 
\vspace{.5cm}
\end{center}
\doublespacing 

اینجانب 
\@name \ \@surname \
تأیید می‌کنم که مطالب مندرج در این \@projectLabel{} حاصل کار پژوهشی اینجانب است و به دستاوردهای پژوهشی دیگران که در این نوشته از آن‌ها استفاده شده است مطابق مقررات ارجاع گردیده است. این \@projectLabel{} قبلاً برای احراز هیچ مدرک هم‌سطح یا بالاتری ارائه نشده است.

\vspace{1cm}
\begin{center}
\begin{tabular}{lr}
نام و نام خانوادگی دانشجو:   & 	\@name{} \@surname \\
 تاریخ و امضای دانشجو: & \\
\end{tabular}
\end{center}
}

% بخش تقدیم
%% پایان‌نامه خود را تقدیم کنید! %%
\newcommand{\taghdimPage}
{
\ifx\@dedication\undefined%
    \relax
\else
    \cleardoublepage
    \thispagestyle{empty}
    %\vspace{-40mm}
    \@dedication
\fi
}

%% قدردانی (فارسی)
\newcommand{\ghadrdaniPage}
{
\ifx\@acknowledgement\undefined%
	\relax
\else   
	\cleardoublepage
	\thispagestyle{empty}
	\centerline{\Large \bfseries سپاس گزاری}
	\vspace{1cm}
	\par\noindent
	\@acknowledgement
% با استفاده از دستور زیر، امضای شما، به طور خودکار، درج می‌شود.
	\signature 
\fi
}

%% چکیده فارسی
\newcommand\abstractPage{
	\cleardoublepage
	\thispagestyle{empty}
	\section*{چکیده}
	% \addcontentsline{toc}{section}{چکیده}
	\@fa-abstract
	\par\noindent
	\paragraph{واژگان کلیدی}
	\@keywords
}



\AtBeginDocument{
\makeatletter
% تعیین مشخصات فایل PDF
\hypersetup{
pdftitle={document},
pdfauthor={\@name \@surname},
pdfsubject={Thesis in \@subject},
pdfkeywords={\@keywords},
pdfdirection={R2L}
}
\makeatother
}

%%
%% Author: Mehrdad ghassabi
%%
%% Base author: Sina Momken ,M.Amintoosi
%% 
%% Copyright © 2022-     by Mehrdad ghassabi (isfahan-thesis)
%% Copyright © 2019-     by Hadi Safari (tehran-thesis)
%% Copyright © 2017-     by Sina Momken (tehran-thesis)
%% Copyright © 2012-2012 by Mahmood Amintoosi (IUST-Thesis)
%% Copyright © 2011-2012 by Vahid Damanafshan (Tabriz_thesis)
%% Copyright © 2008-2011 by Vafa Khalighi (xepersian-thesis.cls)
%% 
%% Distributable under GNU GENERAL PUBLIC LICENSE Version 3.
%% 
%% This work is "maintained" by Sina Momken & Hadi Safari.
%% 
%% Special thanks to Mahmood Amintoosi, Vahid Damanafshan and Vafa Khalighi for their invaluable work.
%%
%% End of file `tehran-thesis.cls'.
