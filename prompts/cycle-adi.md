---
name: "fpf:cycle-adi"
description: "Executes the FPF Canonical Reasoning Cycle (Abduction -> Deduction -> Induction) to solve a problem with high assurance."
arguments:
  - problem_statement
  - hypotheses_count
---

System Role: FPF ADI Reasoning Engine.

Objective: Solve the problem using the Canonical ADI Cycle (B.5).

**Input Problem:** "{{ problem_statement }}"

**Step 1: Abduction (The Generator)**

- _Action:_ Generate a set of {{ hypotheses_count }} Competing Hypotheses ($H_1, H_2, ..., H_{{hypotheses_count}}$) that explain the Input.
- _Constraint:_ Hypotheses must be falsifiable.
- _SoTA Check:_ Do these hypotheses align with current State-of-the-Art knowledge?

**Step 2: Deduction (The Predictor)**

- _Action:_ For each Hypothesis $H_i$, derive a set of Necessary Consequences ($C_{i,1}, C_{i,2}$).
- _Logic:_ "If $H_i$ is true, then $C_{i,j}$ MUST be observable."

**Step 3: Induction (The Validator)**

- _Action:_ Check available Evidence (Data, Logs, Context) against the Consequences.
- _Scoring:_
    - Corroborated: Evidence found.
    - Refuted: Counter-evidence found.
    - Unknown: No data.

**Final Synthesis:**

- **Leading Hypothesis:**
- **Trust Tuple:** $\langle F, G, R \rangle$
- **Next Action:** What specific evidence should be collected next to resolve ambiguity?

---
# Appendix: Referenced FPF Patterns

## B.5 - Canonical Reasoning Cycle

### B.5:1 - **Problem Frame**

While preceding patterns define the anatomy of trust (`Assurance Levels` in B.3) and the structure of holons (A.1, A.14), they do not specify the cognitive "engine" that drives the creation and evolution of knowledge within FPF. A framework for thinking must provide more than just a filing system for conclusions; it must offer a repeatable, rigorous method for arriving at them, especially when confronting novel, complex, or ill-defined problems.

### B.5:2 - **Problem**

Without a formal, shared reasoning cycle, teams and individuals fall into predictable cognitive traps that stall progress and hide risks:

1.  **Analysis Paralysis:** Teams get stuck endlessly debating existing assumptions, running deductions within a closed world of known facts without a mechanism to introduce genuinely new ideas.
2.  **Blind Empiricism:** Teams engage in unstructured, expensive trial-and-error, running tests and gathering data (induction) without a clear, falsifiable hypothesis to guide their efforts.
3.  **Innovation Gap:** In the face of a problem where existing knowledge is insufficient, there is no formal "permission" or process to generate a creative, plausible guess—the essential first step of any breakthrough.

These pathologies lead to wasted resources, circular debates, and a failure to solve the very problems that require first-principles thinking.

### B.5:3 - **Forces**

| Force | Tension |
| :--- | :--- |
| **Rigor vs. Innovation** | How can we encourage creative, "out-of-the-box" hypotheses while maintaining formal discipline and verifiability? |
| **Certainty vs. Progress** | How can we act and learn systematically when faced with incomplete information and uncertainty? |
| **Theory vs. Practice** | How do we ensure that abstract models and formal deductions are continuously anchored to real-world evidence and empirical validation? |
| **Systematic Flow** | How do we transform problem-solving from a chaotic, ad-hoc art into a repeatable, auditable, and teachable science? |

### B.5:4 - **Solution**

FPF establishes the **Abductive–Deductive–Inductive Loop** as its canonical reasoning cycle. This cycle gives formal primacy to **abduction** (hypothesis generation) as the engine of innovation, while using deduction and induction as the rigorous mechanisms for testing and refining those hypotheses.

The loop consists of three distinct, sequential phases:

#### B.5:4.1 - Abduction (Hypothesis Generation)

*   **Core Question:** "What is the most plausible new explanation or solution?"
*   **Description:** This is the creative, inventive leap. When faced with an anomaly, a design challenge, or an unanswered question, the first step is to propose a new `U.Episteme`—a new requirement, a new component, a new causal link—that *might* solve the problem. This act is not guaranteed to be correct; it is a conjecture. Within FPF, this new, untested artifact typically begins its life at **`AssuranceLevel:L0 (Unsubstantiated)`**. Abduction is the only phase that introduces genuinely novel ideas into the model. This formalizes the process described in the **Abductive Loop** (Pattern B.5.2).

#### B.5:4.2 - Deduction (Consequence Derivation)

*   **Core Question:** "If this hypothesis is true, what logically follows?"
*   **Description:** This is the phase of rigorous analysis. Given the new hypothesis, we use the formal models and calculi of FPF to deduce its logical consequences. What are its testable predictions? Does it create internal contradictions with other parts of the model? How does it propagate through the system? This phase aligns with **Verification Assurance (VA)** and is concerned with raising the artifact's **FormalVerifiabilityScore (FV)**. Deduction turns a plausible idea into a set of precise, falsifiable claims.

#### B.5:4.3 - Induction (Empirical Evaluation)

*   **Core Question:** "Do the predicted consequences match reality?"
*   **Description:** This is the phase of testing and learning from evidence. The predictions derived in the deductive phase are compared against real-world data from experiments, simulations, or observations. This phase aligns with **Validation Assurance (LA)** and is the primary mechanism for raising an artifact's **EmpiricalValidabilityScore (EV)** and, consequently, its **Reliability (R)**. A successful test corroborates the hypothesis (raising its `AssuranceLevel`), while a failed test (a refutation) provides critical new information that feeds back into the next abductive cycle.

#### B.5:4.4 - **Didactic Note for Managers: The "Propose → Analyze → Test" Cycle**
> 
> The Abductive-Deductive-Inductive loop is not an abstract philosophical concept; it is the formal name for the problem-solving cycle that all successful R&D and engineering teams instinctively use.
> 
> | Phase | Simple Name | What Your Team Does | FPF's Contribution |
> | :--- | :--- | :--- | :--- |
> | **Abduction** | **Propose** | Brainstorms a new feature, architecture, or fix. | Gives formal permission for this creative step and a place to record the new idea (`L0` artifact). |
| **Deduction** | **Analyze** | Thinks through the implications, runs simulations, checks for conflicts. | Provides the formal models (VA, FV) to make this analysis rigorous and repeatable. |
| **Induction** | **Test** | Builds a prototype, runs A/B tests, gathers user feedback. | Provides the framework (LA, EV, R) to measure the results and build an auditable evidence base. |
> 
> By making this cycle explicit, FPF transforms problem-solving from a chaotic art into a repeatable, auditable science. It gives teams a shared map for navigating from an unknown problem to a validated solution.

### B.5:5 - **Conformance Checklist**

To ensure the reasoning cycle is applied consistently and rigorously, the following criteria are normative:

*   **CC-B5.1 (Abductive Primacy):** Any discipline that introduces a new, non-derivable claim or design element into a working model **MUST** document it as an abductive step. The resulting artifact **SHALL** initially be assigned `AssuranceLevel:L0`.
*   **CC-B5.2 (Deductive Mandate):** An abductively generated hypothesis **SHALL NOT** be subjected to inductive testing (Validation Assurance) until its key logical consequences have been derived and documented through a deductive process.
*   **CC-B5.3 (Inductive Grounding):** A claim **SHALL NOT** be promoted to `AssuranceLevel:L1` or higher on the basis of a successful inductive test unless that test is explicitly linked to a prediction derived in the deductive phase.
*   **CC-B5.4 (Cycle Closure):** The outcome of an inductive test (whether corroboration or refutation) **MUST** be formally recorded as an evidence artifact (Pattern A.10), and this artifact **MUST** be used as an input for the next iteration of the reasoning cycle.
*   **CC-B5.5 (State Machine Alignment):** The Abductive–Deductive–Inductive Loop is the cognitive engine that drives state transitions in the **Explore → Shape → Evidence → Operate** state machine (Pattern B.5.1). Abduction dominates the *Explore* phase; Deduction dominates the *Shape* phase; and Induction is the core of the *Evidence* phase.

## B.5.1 - Explore → Shape → Evidence → Operate

### B.5.1:1 - **Problem Frame**

Every successful innovation, from a new piece of software to a scientific theory, follows a predictable evolution (state transitions). It begins as a fuzzy idea, is gradually given a clear structure, is tested against reality, and finally, is put into operational use. Without a shared map of this lifecycle, teams often get stuck: developers might endlessly refine a structure without testing it, while analysts might gather evidence for an idea that has not yet been clearly defined.

### B.5.1:2 - **Problem**

How do we provide a simple, universal state machine that guides an artifact's journey from a raw concept to a reliable, operational holon? This pattern defines the four canonical states of this journey, providing a clear roadmap for teams and a stable framework for project management.

### B.5.1:3 - **Solution**

FPF defines a four-state development cycle model for any artifact (`U.Episteme` or `U.System`). An artifact transitions from one state to the next as it accumulates rigor and evidence. This state machine is driven by the **Canonical Reasoning Cycle** (Pattern B.5).

**The Four States of an Artifact's Lifecycle:**

| State | Core Activity | Manager's View: What It Means | Driven by Phase of Reasoning Cycle | Typical `AssuranceLevel` |
| :--- | :--- | :--- | :--- | :--- |
| **1. Exploration** | **Generating possibilities.** The focus is on brainstorming, questioning assumptions, and generating multiple, often competing, hypotheses. | "We are in the 'what if' phase. All ideas are on the table. We are looking for a plausible path forward." | **Abduction** (Pattern B.5.2) | `L0` |
| **2. Shaping** | **Defining a single, coherent form.** The most promising hypothesis from the exploration phase is selected and given a rigorous, internally consistent structure. | "We've chosen our direction. Now we are building the blueprint, defining the architecture, and ensuring all the pieces fit together logically."| **Deduction** | `L0` → `L1` (if formalization counts as TA) |
| **3. Evidence** | **Testing against reality.** The shaped artifact is subjected to rigorous empirical or formal tests to validate its claims and measure its performance. | "The blueprint is done. Now we are at the proving ground. Does it actually work? We are running the tests and gathering the data." | **Induction** | `L1` → `L2` |
| **4. Operation** | **Deploying and monitoring in a live environment.** The validated artifact is put into production, where it performs its intended function and is monitored for ongoing reliability. | "It's live. The system is in service, delivering value, and we are monitoring its health and performance." | Continuous Induction (Monitoring) | `L2` (maintained) |

> **Didactic Note for Managers: Aligning States with Your Project Plan**
> 
> This state machine is not an abstract theory; it maps directly to the familiar phases of any well-run project.
> 
> *   **Exploration** is your R&D or initial discovery sprint.
> *   **Shaping** is your design and architecture phase.
> *   **Evidence** is your QA, testing, and V&V phase.
> *   **Operation** is the live deployment and maintenance phase.
> 
> By using these four states, you can instantly communicate to your team and stakeholders exactly where an artifact is in its state transition, what the current focus is, and what needs to happen to move to the next stage.

### B.5.1:4 - **Conformance Checklist**

*   **CC-B5.1.1 (State Explicitness):** Every artifact in a project **MUST** be tagged with its current state from the set {Exploration, Shaping, Evidence, Operation}.
*   **CC-B5.1.2 (Sequential Progression):** An artifact **SHALL** progress through the states in sequence. Skipping a state (e.g., moving directly from Exploration to Evidence without Shaping) is a process violation and must be explicitly justified in the artifact's rationale.
*   **CC-B5.1.3 (Reasoning Cycle Alignment):** The transition between states **MUST** be triggered by the completion of the corresponding phase of the Canonical Reasoning Cycle (Pattern B.5). For example, the transition from *Shaping* to *Evidence* requires the completion of the deductive analysis.

## B.5.2 - Abductive Loop

### B.5.2:1 - **Problem Frame**

The Canonical Reasoning Cycle (Pattern B.5) begins with abduction—the act of generating a new hypothesis. While deduction and induction have well-understood formalisms, abduction is often treated as a mysterious "black box" of creativity, a moment of insight that cannot be managed or systematized. This view is both impractical and incorrect. In engineering, research, and strategy, the ability to generate high-quality, plausible hypotheses is the single most critical driver of innovation.

### B.5.2:2 - **Problem**

Without a formal method for abduction, teams are left to rely on unstructured brainstorming or individual genius. This leads to several failure modes:

1.  **Innovation Deadlock:** When faced with a problem that cannot be solved with existing knowledge, the team has no formal next step. They are stuck, waiting for a "eureka" moment that may never come.
2.  **Untraceable Origins:** When a new idea does emerge, its origins are often unrecorded. The context, the discarded alternatives, and the initial rationale are lost, making it impossible to audit or learn from the decision later.
3.  **Low-Quality Hypotheses:** Without a guiding structure, brainstorming can produce a wide range of ideas, but many may be untestable, irrelevant, or based on flawed assumptions.

### B.5.2:3 - **Forces**

| Force | Tension |
| :--- | :--- |
| **Creativity vs. Discipline** | How can we encourage bold, imaginative leaps while ensuring they are grounded, plausible, and lead to testable outcomes? |
| **Speed vs. Rigor** | How can we generate new ideas quickly without sacrificing the analytical rigor needed to vet them? |
| **Openness vs. Focus** | How can we explore a wide range of possibilities without getting lost in endless, unproductive speculation? |

### B.5.2:4 - **Solution**

FPF operationalizes abduction not as a single moment of insight, but as a structured, iterative **Abductive Loop**. This loop provides a repeatable method for generating and refining high-quality hypotheses.

#### B.5.2:4.1 - The Nature of Abduction in FPF**

In FPF, abduction is defined as **inference to the most plausible explanation or solution**. It is not a random guess. It is a reasoned, albeit non-deductive, process of proposing a new `U.Episteme` that, if true, would best explain a surprising observation or solve a pressing problem.

#### B.5.2:4.2 - The Abductive Loop: A Four-Step Micro-Cycle

The loop provides a scaffold for this inference process:

| Step | Core Activity | Manager's View: What Your Team is Doing |
| :--- | :--- | :--- |
| **1. Frame the Anomaly** | **Isolate the surprise.** The team clearly articulates the specific observation, conflict, or goal that cannot be explained or achieved with the current model. | "Let's be crystal clear about the one specific thing we don't understand or can't solve right now." |
| **2. Generate Candidate Hypotheses** | **Brainstorm explanations.** The team generates a set of candidate hypotheses, each proposing a new entity, relation, or rule that could account for the anomaly. | "What are all the possible reasons for this? Let's get them all on the table, from the obvious to the radical." |
| **3. Apply Plausibility Filters** | **Rank the candidates.** The team assesses each hypothesis against a set of plausibility criteria (e.g., simplicity, consistency with known principles, explanatory power). | "Which of these ideas are actually worth pursuing? Which are too complex, contradict known facts, or don't really solve the problem?" |
| **4. Select & Formalize the Prime Hypothesis** | **Choose the best bet.** The most plausible hypothesis is selected and formally documented as a new `U.Episteme` artifact with `AssuranceLevel:L0`. | "We've got our lead. Let's write it down as a formal, testable claim and move it to the next stage of the reasoning cycle." |

This loop can be cycled through rapidly. An initial "prime hypothesis" might be quickly refined or replaced as the team deepens its understanding by applying the plausibility filters.

## B.5.2.1 - Creative Abduction with NQD

**Status.** Normative **binding** to **B.5.2 Abductive Loop** that delegates candidate generation to **Γ_nqd.generate** (**C.18 NQD-CAL**) and exploration/exploitation policy to **E/E-LOG (C.19)**; the kernel remains unchanged.

### B.5.2.1:2 - Intent & Problem

**Intent.** Turn Step 2 (*generate*) and Step 3 (*filter*) of the Abductive Loop from ad‑hoc brainstorming into a **disciplined, instrumented exploration** that can (i) *produce many* distinct, plausible hypotheses and (ii) *surface the few worth pursuing*—*without* bloating the kernel or forcing a specific creative method.

## B.5.3 - Role-Projection Bridge

### B.5.3:1 - **Problem Frame**

The FPF is built upon a small set of universal, domain-agnostic concepts (`U.Types`) like `U.System`, `U.Objective`, and `U.State`. This universality is the source of its power, allowing it to be applied to any domain, from thermodynamics to software engineering. However, practitioners in these domains do not speak in terms of `U.Types`; they use their own rich, specialized vocabularies.

### B.5.3:4 - **Solution**

FPF solves this with the **Role-Projection Pattern**, a mechanism that creates a robust, semantically rich **Concept-Bridge** between the universal kernel and domain-specific vocabularies. This pattern is built on three core components:

#### B.5.3:4.1 - The `Role` Concept

*   **Description:** FPF introduces a new universal type, `U.Role`. A `Role` is not a concrete thing but an **abstract, context-dependent role** that an entity can play. It represents the domain-specific *interpretation* of a universal concept.
*   **Example:** "Thermodynamic System" is not modeled as a new subtype of `U.System`. Instead, it is modeled as a `Role` that a `U.System` can *play* when it is being analyzed from a thermodynamic perspective.

## B.3 - Trust & Assurance Calculus (F–G–R)

### B.3:4.1 - The F–G–R characteristics (CHR‑compliant)

We standardize three characteristics on **nodes (holons)** plus one **edge** characteristic:

1. **Formality (F)** — *how constrained the reasoning is by explicit, proof‑grade structure.*
   * **Scale kind:** **ordinal** (levels do not admit arithmetic).
   * **Canonical levels (example):** `F0 Informal prose` - `F1 Structured narrative` - `F2 Formalizable schema` - `F3 Proof‑grade formalism`.
   * **Monotone direction:** higher is better (never lowers assurance when all else fixed).

2. **ClaimScope (G)** — *how broadly the result applies in the relevant domain space.*
   * **Scale kind:** **coverage / span** (set‑ or measure‑based; domain‑specific).
   * **Monotone direction:** larger, but only when **correctly supported** (see WLNK and CL below).

3. **Reliability (R)** — *how likely the claim/behavior holds under stated conditions.*
   * **Scale kind:** **ratio** in `[0,1]` (or a conservative ordinal proxy when numeric modeling is unavailable).
   * **Monotone direction:** higher is better.

4. **Congruence Level (CL)** — *edge property: how well two parts fit* (semantic alignment, calibration, interface Standard).
   * **Scale kind:** **ordinal** with a **monotone penalty function** `Φ(CL)` where `Φ` decreases as CL increases.
